package com.example.finalproject.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finalproject.ui.Session.OnboardingViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

data class UserMatch(val uid: String, val name: String, val commonHobbies: List<String>)

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

    // Colores personalizados fosforescentes
    val turquoise = Color(0xFF1DE9B6)
    val pinkNeon = Color(0xFFFF69B4)
    val darkBg = Color(0xFF23243A)

    LaunchedEffect(currentUid) {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentUserHobbies = snapshot.child(currentUid ?: "")
                    .child("hobbies").children.mapNotNull { it.getValue(String::class.java) }

                val otherUsers = snapshot.children.filter { it.key != currentUid }

                val matchResults = otherUsers.mapNotNull { userSnapshot ->
                    val uid = userSnapshot.key ?: return@mapNotNull null
                    val name = userSnapshot.child("name").getValue(String::class.java) ?: return@mapNotNull null
                    val hobbies = userSnapshot.child("hobbies").children.mapNotNull { it.getValue(String::class.java) }
                    val common = currentUserHobbies.intersect(hobbies.toSet())
                    if (common.isNotEmpty()) UserMatch(uid, name, common.toList()) else null
                }

                matches = matchResults
                isLoading = false
            }

            override fun onCancelled(error: DatabaseError) {
                isLoading = false
            }
        })
    }

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
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.97f)
                .padding(18.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(18.dp)
            ) {
                Text(
                    text = "Coincidencias por hobbies",
                    style = MaterialTheme.typography.headlineSmall,
                    color = pinkNeon,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 10.dp)
                )

                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = turquoise)
                    }
                } else if (matches.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No se encontraron coincidencias.", color = darkBg)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(bottom = 12.dp)
                    ) {
                        items(matches) { match ->
                            Card(
                                shape = RoundedCornerShape(18.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = turquoise.copy(alpha = 0.22f)
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            match.name,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = pinkNeon,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            "Coincidencias: ${match.commonHobbies.joinToString(", ")}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = darkBg
                                        )
                                    }
                                    Button(
                                        onClick = {
                                            navController.navigate("chat/${match.name}/${match.uid}")
                                        },
                                        shape = RoundedCornerShape(14.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = pinkNeon,
                                            contentColor = Color.White
                                        ),
                                        modifier = Modifier.height(42.dp)
                                    ) {
                                        Text("Chat", fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}