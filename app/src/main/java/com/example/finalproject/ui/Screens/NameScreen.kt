package com.example.finalproject.ui.Screens

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

@Composable
fun NameScreen(
    onContinue: () -> Unit = {},
    onClose: () -> Unit = {},
    navController: NavController
) {
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
            onValueChange = { name = it },
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
            onClick = {navController.navigate(route = AppScreens.BirthdayScreen.route)},
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("CONTINUAR")
        }
    }
}
