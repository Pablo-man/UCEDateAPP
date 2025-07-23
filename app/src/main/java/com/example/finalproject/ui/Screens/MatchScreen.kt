package com.example.finalproject.ui.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finalproject.ui.Session.OnboardingViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

data class UserMatch(val name: String, val commonHobbies: List<String>)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchScreen(matches: List<UserMatch>, onboardingViewModel: OnboardingViewModel, navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            "Usuarios con hobbies similares a los tuyos",
            style = MaterialTheme.typography.titleMedium
        )

        if (matches.isEmpty()) {
            Text("No hay coincidencias por ahora.")
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(matches) { match ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(text = match.name, style = MaterialTheme.typography.titleSmall)
                                Text(
                                    text = "Coincidencias: ${match.commonHobbies.joinToString(", ")}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Button(onClick = {
                                navController.navigate("chat/${match.name}")
                            }) {
                                Text("Chat")
                            }
                        }
                    }
                }
            }
        }
    }
}

