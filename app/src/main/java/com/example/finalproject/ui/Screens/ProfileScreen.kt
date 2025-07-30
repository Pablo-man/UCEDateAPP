package com.example.finalproject.ui.Screens

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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

    val turquoise = Color(0xFF1DE9B6)
    val pinkNeon = Color(0xFFFF69B4)
    val darkBg = Color(0xFF23243A)

    val scrollState = rememberScrollState()

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

                    // 🔥 Guardar en el ViewModel
                    viewModel.photo = cloudinaryUrl
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

    viewModel.name= name
    viewModel.state= state
    viewModel.career = career
    viewModel.semester = semester
    viewModel.gender = gender
    viewModel.birthDate = birthDate
    viewModel.hobbies = hobbies

    val photoUrl = auth.currentUser?.photoUrl?.toString()
        ?: "https://cdn-icons-png.flaticon.com/512/149/149071.png"

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
                .fillMaxHeight(0.98f)
                .padding(18.dp)
        ) {

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(horizontal = 24.dp)
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

            Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .background(pinkNeon.copy(alpha = 0.15f))
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

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = name,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = pinkNeon
                )

                Text(
                    text = state,
                    style = MaterialTheme.typography.bodyMedium,
                    color = turquoise,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(26.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = turquoise.copy(alpha = 0.22f)),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        ProfileField(label = "Carrera", value = career)
                        ProfileField(label = "Semestre", value = semester)
                        ProfileField(label = "Género", value = gender)
                        ProfileField(label = "Fecha de nacimiento", value = birthDate)
                        if (hobbies.isNotEmpty()) {
                            ProfileField(label = "Hobbies", value = hobbies.joinToString(", "))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(26.dp))

                Button(
                    onClick = { navController.navigate("name_screen") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = pinkNeon,
                        contentColor = Color.White
                    )
                ) {
                    Text("Editar perfil", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { navController.navigate("hobbie_screen") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = turquoise,
                        contentColor = Color.White
                    )
                ) {
                    Text("Editar gustos", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { navController.navigate("match_screen") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = darkBg,
                        contentColor = Color.White
                    )
                ) {
                    Text("Buscar coincidencias", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun ProfileField(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = Color(0xFF23243A)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Divider(modifier = Modifier.padding(vertical = 4.dp), color = Color(0xFF1DE9B6))
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
            .url("http://13.217.146.85:3000/upload")
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