package com.example.finalproject.ui.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HobbiesScreen(navController: NavController) {
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
                // Puedes guardar los hobbies si quieres
                navController.navigate("profile")  // Regresa a ProfileScreen
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }
    }
}
