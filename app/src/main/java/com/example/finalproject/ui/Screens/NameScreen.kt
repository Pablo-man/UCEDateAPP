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
import androidx.navigation.NavBackStackEntry
import kotlinx.coroutines.launch


@Composable
fun NameScreen(
    onClose: () -> Unit = {},
    navController: NavController,
    viewModel: OnboardingViewModel
) {

    val coroutineScope = rememberCoroutineScope()
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

        val isValid = name.length >= 4

        // Botón continuar
        Button(
            onClick = {
                coroutineScope.launch {
                    navController.navigate(route = AppScreens.GenderScreen.route)
                }
            },
            enabled = isValid,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("CONTINUAR")
        }

    }
}

