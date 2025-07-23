package com.example.finalproject.ui.Screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.compose.AsyncImage
import com.example.finalproject.ui.Session.OnboardingViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.ktor.client.HttpClient
import io.ktor.client.request.get

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: OnboardingViewModel
) {

    val name = viewModel.name

    val photoUrl = FirebaseAuth.getInstance().currentUser?.photoUrl?.toString()
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
            text = name.ifEmpty { "Usuario UCE" },
            style = MaterialTheme.typography.headlineSmall
        )

        Button(
            onClick = { navController.navigate("name_screen") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Editar perfil")
        }

        Button(
            onClick = { /* Acción para buscar */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Encontrar")
        }

        Button(
            onClick = { navController.navigate("hobbie_screen") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Editar Gustos")
        }
    }
}
