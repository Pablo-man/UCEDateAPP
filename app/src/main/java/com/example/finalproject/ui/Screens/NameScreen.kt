package com.example.finalproject.ui.Screens

import kotlinx.serialization.Serializable
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.finalproject.ui.Navigation.AppScreens
import com.example.finalproject.ui.Session.OnboardingViewModel
import kotlinx.coroutines.launch

@Composable
fun NameScreen(
    onClose: () -> Unit = {},
    navController: NavController,
    viewModel: OnboardingViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var name by remember { mutableStateOf("") }
    val isValid = name.length >= 4

    // Colores institucionales
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
                containerColor = Color.White.copy(alpha = 0.90f)
            ),
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Cerrar (X)
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopStart
                ) {
                    IconButton(onClick = onClose) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = pinkNeon
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Título
                Text(
                    text = "Mi\nnombre es",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    color = pinkNeon,
                    lineHeight = 40.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Campo de texto
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        viewModel.name = it
                    },
                    label = { Text("Nombre Completo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = turquoise,
                        focusedLabelColor = turquoise,
                        cursorColor = pinkNeon
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Subtexto
                Text(
                    text = "En la aplicación se te va a conocer así.",
                    fontSize = 15.sp,
                    color = darkBg.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.weight(1f))

                // Botón continuar
                Button(
                    onClick = {
                        coroutineScope.launch {
                            navController.navigate(route = AppScreens.BirthdayScreen.route)
                        }
                    },
                    enabled = isValid,
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
                        "CONTINUAR",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}