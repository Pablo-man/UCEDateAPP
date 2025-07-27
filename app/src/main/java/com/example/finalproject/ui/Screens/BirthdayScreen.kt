package com.example.finalproject.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.finalproject.ui.Navigation.AppScreens
import com.example.finalproject.ui.Session.OnboardingViewModel

@Composable
fun BirthdayScreen(
    onBack: () -> Unit = {},
    navController: NavController,
    viewModel: OnboardingViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    var year by remember { mutableStateOf(viewModel.birthYear) }
    var month by remember { mutableStateOf(viewModel.birthMonth) }
    var day by remember { mutableStateOf(viewModel.birthDay) }
    val isValid = year.length == 4 && month.length == 2 && day.length == 2

    // Define tus colores personalizados
    val turquoise = Color(0xFF1DE9B6)
    val pinkNeon = Color(0xFFFF69B4)
    val darkBg = Color(0xFF23243A)

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
                containerColor = Color.White.copy(alpha = 0.85f)
            ),
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Flecha de regreso
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopStart
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = turquoise
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Título
                Text(
                    text = "Mi\ncumpleaños es",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    color = pinkNeon,
                    lineHeight = 40.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Campos para año, mes y día
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = year,
                        onValueChange = {
                            if (it.length <= 4) {
                                year = it
                                viewModel.birthYear = it
                            }
                        },
                        label = { Text("YYYY") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = turquoise,
                            focusedLabelColor = turquoise,
                            cursorColor = pinkNeon
                        )
                    )
                    OutlinedTextField(
                        value = month,
                        onValueChange = {
                            if (it.length <= 2) {
                                month = it
                                viewModel.birthMonth = it
                            }
                        },
                        label = { Text("MM") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = turquoise,
                            focusedLabelColor = turquoise,
                            cursorColor = pinkNeon
                        )
                    )
                    OutlinedTextField(
                        value = day,
                        onValueChange = {
                            if (it.length <= 2) {
                                day = it
                                viewModel.birthDay = it
                            }
                        },
                        label = { Text("DD") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = turquoise,
                            focusedLabelColor = turquoise,
                            cursorColor = pinkNeon
                        )
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Subtexto
                Text(
                    text = "Tu edad será pública",
                    fontSize = 15.sp,
                    color = darkBg.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { navController.navigate(route = AppScreens.GenderScreen.route) },
                    enabled = isValid,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = pinkNeon,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        "CONTINUAR",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}