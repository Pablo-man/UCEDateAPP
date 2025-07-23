package com.example.finalproject.ui.Screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Selecciona tus hobbies",
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        LazyColumn {
            items(hobbies) { hobby ->
                val isSelected = selectedHobbies.contains(hobby)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            if (isSelected) selectedHobbies.remove(hobby)
                            else selectedHobbies.add(hobby)
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                        else MaterialTheme.colorScheme.surface
                    )
                ) {
                    Text(
                        text = hobby,
                        modifier = Modifier.padding(16.dp),
                        fontSize = 18.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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
                )

                uid?.let {
                    database.getReference("users")
                        .child(it)
                        .setValue(userData)
                        .addOnSuccessListener {
                            // Navegar o mostrar mensaje
                            navController.navigate(AppScreens.Welcome.route)
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                context,
                                "Error al guardar datos: ${e.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                }
                navController.navigate("profile")  // Regresa a ProfileScreen
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }
    }
}
