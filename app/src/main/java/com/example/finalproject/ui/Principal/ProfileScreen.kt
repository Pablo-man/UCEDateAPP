package com.example.finalproject.ui.Principal


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(navController: NavController) {
    val user = FirebaseAuth.getInstance().currentUser
    val displayName = user?.displayName ?: "Usuario UCE"
    val photoUrl = user?.photoUrl?.toString()
        ?: "https://cdn-icons-png.flaticon.com/512/149/149071.png"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberImagePainter(photoUrl),
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(150.dp)
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = displayName,
            style = MaterialTheme.typography.headlineSmall
        )
        Button(
            onClick = { navController.navigate("editProfile") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Editar perfil")
        }

        Button(
            onClick = { /* Acción para buscar o encontrar personas */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Encontrar")
        }
    }
}
