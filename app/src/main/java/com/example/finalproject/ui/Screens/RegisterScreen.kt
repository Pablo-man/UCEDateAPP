package com.example.finalproject.ui.Screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.finalproject.ui.Navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RegisterScreen(
    navController: NavHostController,
) {

    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Colores institucionales y vibrantes
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
                containerColor = Color.White.copy(alpha = 0.92f)
            ),
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Registrarse",
                    style = MaterialTheme.typography.headlineMedium,
                    color = pinkNeon,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo institucional") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = turquoise,
                        unfocusedBorderColor = turquoise.copy(alpha = 0.45f),
                        focusedLabelColor = turquoise,
                        cursorColor = pinkNeon
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = turquoise,
                        unfocusedBorderColor = turquoise.copy(alpha = 0.45f),
                        focusedLabelColor = turquoise,
                        cursorColor = pinkNeon
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = {
                        if (!email.endsWith("@uce.edu.ec")) {
                            Toast.makeText(context, "Usa un correo @uce.edu.ec", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val user = auth.currentUser
                                    user?.sendEmailVerification()
                                        ?.addOnCompleteListener { verifyTask ->
                                            if (verifyTask.isSuccessful) {
                                                Toast.makeText(
                                                    context,
                                                    "Correo de verificación enviado. Revisa tu bandeja.",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                                navController.navigate(AppScreens.LoginScreen.route)
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "No se pudo enviar el correo de verificación.",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Error: ${task.exception?.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
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
                    Text("Registrarse", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(10.dp))

                TextButton(
                    onClick = {
                        navController.navigate(AppScreens.LoginScreen.route)
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        "¿Ya tienes cuenta? Inicia sesión",
                        color = turquoise,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
                    )
                }
            }
        }
    }
}