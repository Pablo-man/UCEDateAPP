package com.example.finalproject.ui.Screens

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.finalproject.ui.Navigation.AppScreens
import com.example.finalproject.ui.Session.OnboardingViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: OnboardingViewModel
) {

    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showResendButton by remember { mutableStateOf(false) }
    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Iniciar Sesión", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo institucional") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )


        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (!email.endsWith("@uce.edu.ec")) {
                Toast.makeText(context, "Usa solo correos @uce.edu.ec", Toast.LENGTH_SHORT).show()
                return@Button
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(context, "Correo inválido", Toast.LENGTH_SHORT).show()
                return@Button
            }

            if (password.length < 6) {
                Toast.makeText(context, "Contraseña demasiado corta", Toast.LENGTH_SHORT).show()
                return@Button
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        viewModel.uid = auth.currentUser!!.uid

                        val user = auth.currentUser
                        if (user != null && user.isEmailVerified) {
                            Toast.makeText(context, "Bienvenido", Toast.LENGTH_SHORT).show()
                            navController.navigate(AppScreens.Welcome.route) {
                                popUpTo(AppScreens.LoginScreen.route) { inclusive = true }
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Verifica tu correo antes de ingresar.",
                                Toast.LENGTH_LONG
                            ).show()
                            showResendButton = true
                            auth.signOut()
                        }
                    } else {
                        Toast.makeText(context, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
                    }
                }
        }) {
            Text("Ingresar")
        }

        if (showResendButton) {
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = {
                val user = auth.currentUser
                if (user != null && !user.isEmailVerified) {
                    user.sendEmailVerification().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                context,
                                "Correo de verificación enviado nuevamente.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                context,
                                "Error al enviar verificación.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }) {
                Text("Reenviar verificación")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = {
            navController.navigate(AppScreens.RegisterScreen.route)
        }) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}
