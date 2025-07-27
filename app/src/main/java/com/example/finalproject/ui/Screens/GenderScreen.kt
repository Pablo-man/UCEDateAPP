package com.example.finalproject.ui.Screens

import androidx.compose.foundation.BorderStroke
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
fun GenderScreen(
    onBack: () -> Unit = {},
    navController: NavController,
    viewModel: OnboardingViewModel
) {
    var selectedGender by remember { mutableStateOf(viewModel.gender) }

    // Colores personalizados
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
                containerColor = Color.White.copy(alpha = 0.90f)
            ),
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Flecha atrás
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
                    text = "Soy",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    color = pinkNeon,
                    lineHeight = 40.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Opciones de género
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    GenderOptionButton(
                        text = "Hombre",
                        isSelected = selectedGender == "Hombre",
                        onClick = {
                            selectedGender = "Hombre"
                            viewModel.gender = "Hombre"
                        },
                        accentColor = turquoise,
                        neonColor = pinkNeon
                    )
                    GenderOptionButton(
                        text = "Mujer",
                        isSelected = selectedGender == "Mujer",
                        onClick = {
                            selectedGender = "Mujer"
                            viewModel.gender = "Mujer"
                        },
                        accentColor = turquoise,
                        neonColor = pinkNeon
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Botón CONTINUE
                Button(
                    onClick = { navController.navigate(route = AppScreens.CareerScreen.route) },
                    enabled = selectedGender != null,
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

@Composable
fun GenderOptionButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    accentColor: Color,
    neonColor: Color,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        border = BorderStroke(
            width = 2.dp,
            color = if (isSelected) neonColor else accentColor
        ),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (isSelected) accentColor.copy(alpha = 0.15f) else Color.White,
            contentColor = if (isSelected) neonColor else accentColor
        )
    ) {
        Text(
            text = text,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            fontSize = 18.sp
        )
    }
}