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
fun MatchScreen(
    navController: NavController,
    viewModel: OnboardingViewModel
) {
    val database = FirebaseDatabase.getInstance().reference.child("users")
    val auth = FirebaseAuth.getInstance()
    val currentUid = auth.currentUser?.uid

    var matches by remember { mutableStateOf(listOf<UserMatch>()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(currentUid) {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentUserHobbies = snapshot.child(currentUid ?: "")
                    .child("hobbies").children.mapNotNull { it.getValue(String::class.java) }

                val otherUsers = snapshot.children.filter { it.key != currentUid }

                val matchResults = otherUsers.mapNotNull { userSnapshot ->
                    val name = userSnapshot.child("name").getValue(String::class.java) ?: return@mapNotNull null
                    val hobbies = userSnapshot.child("hobbies").children.mapNotNull { it.getValue(String::class.java) }
                    val common = currentUserHobbies.intersect(hobbies.toSet())
                    if (common.isNotEmpty()) UserMatch(name, common.toList()) else null
                }

                matches = matchResults
                isLoading = false
            }

            override fun onCancelled(error: DatabaseError) {
                isLoading = false
            }
        })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Coincidencias por hobbies") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)) {

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 32.dp))
            } else if (matches.isEmpty()) {
                Text("No se encontraron coincidencias.")
            } else {
                LazyColumn {
                    items(matches) { match ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(match.name, style = MaterialTheme.typography.titleMedium)
                                Text(
                                    "Coincidencias: ${match.commonHobbies.joinToString(", ")}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
