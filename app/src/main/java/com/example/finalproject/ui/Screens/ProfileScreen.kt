package com.example.finalproject.ui.Screens

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.finalproject.ui.Session.OnboardingViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: OnboardingViewModel
) {
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    val uid = auth.currentUser?.uid

    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageUri = it

            // Aquí subes a tu backend que sube a Cloudinary
            uploadImageToBackend(
                context = context,
                imageUri = it,
                uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            ) { cloudinaryUrl ->
                if (cloudinaryUrl != null) {
                    // Actualizar el perfil del usuario con la nueva URL
                    val user = FirebaseAuth.getInstance().currentUser
                    val profileUpdate = UserProfileChangeRequest.Builder()
                        .setPhotoUri(Uri.parse(cloudinaryUrl))
                        .build()
                    user?.updateProfile(profileUpdate)
                }
            }
        }
    }


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
                title = { Text("") },
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

            Box(
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .clickable {
                        imagePickerLauncher.launch("image/*")
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(imageUri ?: photoUrl),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )
            }


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

fun uploadImageToBackend(
    context: Context,
    imageUri: Uri,
    uid: String,
    onResult: (String?) -> Unit
) {
    try {
        val inputStream = context.contentResolver.openInputStream(imageUri)
        val imageBytes = inputStream?.readBytes()
        if (imageBytes == null) {
            Log.e("UPLOAD_ERROR", "No se pudo leer la imagen desde la URI")
            onResult(null)
            return
        }

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "image", "profile.jpg",
                imageBytes.toRequestBody("image/*".toMediaTypeOrNull())
            )
            .addFormDataPart("uid", uid)
            .build()

        val request = Request.Builder()
            .url("http://54.210.210.110:3000/upload")
            .post(requestBody)
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("UPLOAD_ERROR", "Error de red o servidor", e)
                onResult(null)
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    Log.e("UPLOAD_ERROR", "Respuesta fallida del servidor: ${response.code}")
                    onResult(null)
                    return
                }

                val bodyString = response.body?.string()
                if (bodyString == null) {
                    Log.e("UPLOAD_ERROR", "Respuesta vacía del servidor")
                    onResult(null)
                    return
                }

                try {
                    val json = JSONObject(bodyString)
                    val imageUrl = json.getString("url")
                    Log.d("UPLOAD_SUCCESS", "Imagen subida correctamente: $imageUrl")
                    onResult(imageUrl)
                } catch (e: Exception) {
                    Log.e("UPLOAD_ERROR", "Error al parsear la respuesta JSON", e)
                    onResult(null)
                }
            }
        })

    } catch (e: Exception) {
        Log.e("UPLOAD_ERROR", "Excepción general al subir imagen", e)
        onResult(null)
    }
}
