package com.example.finalproject.ui.Screens

import kotlinx.serialization.Serializable
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.finalproject.ui.Navigation.AppScreens
import com.example.finalproject.ui.Session.OnboardingViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch


@Composable
fun NameScreen(
    onClose: () -> Unit = {},
    navController: NavController,
    viewModel: OnboardingViewModel = viewModel()
) {

    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Cerrar (X)
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopStart
        ) {
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Título
        Text(
            text = "Mi\nnombre es",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Campo de texto
        var name by remember { mutableStateOf("") }

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                viewModel.name = it
            },
            label = { Text("Nombre Completo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Subtexto
        Text(
            text = "En la aplicación se te va a conocer así.\n",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.weight(1f))

        // Botón continuar
        Button(
            onClick = {
                coroutineScope.launch {
                    isLoading = true
                    try {
                        sendUserDataToServer(viewModel)
                        // Solo navegar si se envió bien
                        navController.navigate(route = AppScreens.BirthdayScreen.route)
                        navController.navigate(AppScreens.HomeScreen.route) {
                            popUpTo(AppScreens.NameScreen.route) { inclusive = true }
                        }
                    } catch (e: Exception) {
                        errorMessage = "Error al guardar los datos. Intenta de nuevo."
                        Log.e("Registro", "Excepción al enviar datos", e)
                    } finally {
                        isLoading = false
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("CONTINUAR")
        }

    }
}

suspend fun sendUserDataToServer(viewModel: OnboardingViewModel) {
    val userData = mapOf(
        "name" to viewModel.name
        // más campos...
    )

    Log.d("Registro", "Intentando enviar datos al backend...")
    val client = HttpClient {
        install(ContentNegotiation) {
            json() // usa kotlinx.serialization
        }
    }
    val response = client.post("https://8501ba66fa47.ngrok-free.app/") {
        contentType(ContentType.Application.Json)
        setBody(UserData(name = viewModel.name))
    }
    Log.d("Registro", "Código de respuesta: ${response.status}")


    if (response.status == HttpStatusCode.OK) {
        Log.d("Registro", "Usuario registrado correctamente")
    }
}

@Serializable
data class UserData(val name: String)


