package com.example.finalproject.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.database.FirebaseDatabase

@Composable
fun PublicProfileScreen(
    navController: NavController,
    userUid: String
) {
    val database = FirebaseDatabase.getInstance()
    val turquoise = Color(0xFF1DE9B6)
    val pinkNeon = Color(0xFFFF69B4)
    val darkBg = Color(0xFF23243A)

    var name by remember { mutableStateOf("Cargando...") }
    var state by remember { mutableStateOf("") }
    var career by remember { mutableStateOf("") }
    var semester by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var hobbies by remember { mutableStateOf(listOf<String>()) }
    var photo by remember { mutableStateOf("https://cdn-icons-png.flaticon.com/512/149/149071.png") }

    LaunchedEffect(userUid) {
        val ref = database.getReference("users").child(userUid)
        ref.get().addOnSuccessListener { snapshot ->
            name = snapshot.child("name").getValue(String::class.java) ?: "Sin nombre"
            state = snapshot.child("state").getValue(String::class.java) ?: ""
            career = snapshot.child("career").getValue(String::class.java) ?: ""
            semester = snapshot.child("semester").getValue(String::class.java) ?: ""
            gender = snapshot.child("gender").getValue(String::class.java) ?: ""
            birthDate = snapshot.child("birthDate").getValue(String::class.java) ?: ""
            hobbies = snapshot.child("hobbies").children.mapNotNull { it.getValue(String::class.java) }

            photo = snapshot.child("photo").getValue(String::class.java)
                ?: "https://cdn-icons-png.flaticon.com/512/149/149071.png"
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(turquoise, pinkNeon, darkBg))),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.95f),
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(Modifier.height(24.dp))

                Image(
                    painter = rememberAsyncImagePainter(photo),
                    contentDescription = "Foto de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .background(pinkNeon.copy(alpha = 0.15f))
                )

                Spacer(Modifier.height(16.dp))
                Text(name, fontSize = 26.sp, fontWeight = FontWeight.Bold, color = pinkNeon)
                Text(state, color = turquoise)

                Spacer(Modifier.height(20.dp))
                ProfileField("Carrera", career)
                ProfileField("Semestre", semester)
                ProfileField("Género", gender)
                ProfileField("Nacimiento", birthDate)
                if (hobbies.isNotEmpty()) {
                    ProfileField("Hobbies", hobbies.joinToString(", "))
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { navController.popBackStack() },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = darkBg, contentColor = Color.White)
                ) {
                    Text("Volver", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
