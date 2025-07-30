package com.example.finalproject.ui.Screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finalproject.ui.Navigation.AppScreens
import com.example.finalproject.ui.Session.OnboardingViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@Composable
fun HobbiesScreen(
    navController: NavController,
    viewModel: OnboardingViewModel
) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    val uid = auth.currentUser?.uid

    val hobbies = listOf(
        "Música", "Deportes", "Cine", "Lectura", "Videojuegos",
        "Senderismo", "Programación"
    )
    val selectedHobbies = remember { mutableStateListOf<String>() }

    // Colores personalizados fosforescente
    val turquoise = Color(0xFF1DE9B6)
    val pinkNeon = Color(0xFFFF69B4)
    val darkBg = Color(0xFF23243A)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(turquoise, pinkNeon, darkBg)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(28.dp),
            elevation = CardDefaults.cardElevation(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.88f)
            ),
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.Start)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = turquoise)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Selecciona tus hobbies",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = pinkNeon,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp)
                ) {
                    items(hobbies) { hobby ->
                        val isSelected = selectedHobbies.contains(hobby)
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clickable {
                                    if (isSelected) selectedHobbies.remove(hobby)
                                    else selectedHobbies.add(hobby)
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) turquoise.copy(alpha = 0.33f)
                                else Color.White
                            ),
                            elevation = CardDefaults.cardElevation(if (isSelected) 8.dp else 2.dp)
                        ) {
                            Text(
                                text = hobby,
                                modifier = Modifier.padding(16.dp),
                                fontSize = 20.sp,
                                color = if (isSelected) pinkNeon else darkBg
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                Button(
                    onClick = {
                        viewModel.hobbies = selectedHobbies.toList()

                        val userData = mapOf(
                            "uid" to viewModel.uid,
                            "name" to viewModel.name,
                            "birthDate" to viewModel.birthDate,
                            "gender" to viewModel.gender,
                            "career" to viewModel.career,
                            "semester" to viewModel.semester,
                            "state" to viewModel.state,
                            "hobbies" to viewModel.hobbies,
                            "photo" to viewModel.photo
                        )

                        uid?.let {
                            database.getReference("users")
                                .child(it)
                                .setValue(userData)
                                .addOnSuccessListener {
                                    navController.navigate(AppScreens.Profile.route)
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(
                                        context,
                                        "Error al guardar datos: ${e.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }
                        navController.navigate("profile")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = pinkNeon,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        "Guardar",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}