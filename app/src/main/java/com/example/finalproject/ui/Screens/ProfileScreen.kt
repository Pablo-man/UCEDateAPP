package com.example.finalproject.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.finalproject.ui.Session.OnboardingViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: OnboardingViewModel
) {
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    val uid = auth.currentUser?.uid

    var name by remember { mutableStateOf("Cargando...") }
    var state by remember { mutableStateOf("Estado desconocido") }
    var career by remember { mutableStateOf("") }
    var semester by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var hobbies by remember { mutableStateOf(listOf<String>()) }

    LaunchedEffect(uid) {
        uid?.let {
            val data = database.getReference("users").child(it)
            data.get().addOnSuccessListener { snapshot ->
                name = snapshot.child("name").getValue(String::class.java) ?: "Sin nombre"
                state = snapshot.child("state").getValue(String::class.java) ?: "Sin estado"
                career = snapshot.child("career").getValue(String::class.java) ?: "Sin carrera"
                semester = snapshot.child("semester").getValue(String::class.java) ?: "Sin semestre"
                gender = snapshot.child("gender").getValue(String::class.java) ?: "Sin género"
                birthDate = snapshot.child("birthDate").getValue(String::class.java) ?: "Sin fecha"
                hobbies = snapshot.child("hobbies").children.mapNotNull { it.getValue(String::class.java) }
            }
        }
    }

    val photoUrl = auth.currentUser?.photoUrl?.toString()
        ?: "https://cdn-icons-png.flaticon.com/512/149/149071.png"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Image(
                painter = rememberAsyncImagePainter(photoUrl),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = state,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ProfileField(label = "Carrera", value = career)
                    ProfileField(label = "Semestre", value = semester)
                    ProfileField(label = "Género", value = gender)
                    ProfileField(label = "Fecha de nacimiento", value = birthDate)
                    if (hobbies.isNotEmpty()) {
                        ProfileField(label = "Hobbies", value = hobbies.joinToString(", "))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate("name_screen") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Editar perfil")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.navigate("hobbie_screen") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Editar gustos")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.navigate("match_screen") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Buscar coincidencias")
            }


            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ProfileField(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
        Divider(modifier = Modifier.padding(vertical = 4.dp))
    }
}
