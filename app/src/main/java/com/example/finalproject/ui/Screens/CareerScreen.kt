package com.example.finalproject.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.finalproject.ui.Navigation.AppScreens
import com.example.finalproject.ui.Session.OnboardingViewModel

@Composable
fun CareerScreen(
    onBack: () -> Unit = {},
    navController: NavController,
    viewModel: OnboardingViewModel
) {
    var selectedCareer by remember { mutableStateOf(viewModel.career) }
    var selectedSemester by remember { mutableStateOf(viewModel.semester) }

    val careers = listOf("Ingeniería de Software", "Medicina", "Derecho", "Arquitectura")
    val semesters = (1..10).map { it.toString() }

    // Colores personalizados para el fondo y elementos
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
                .fillMaxWidth(0.92f)
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
                    text = "Soy\nestudiante de",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    color = pinkNeon,
                    lineHeight = 40.sp
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Selector de carrera
                Text(
                    "Carrera",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = turquoise,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 4.dp, bottom = 2.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                DropdownMenuField(
                    label = "Selecciona tu carrera",
                    options = careers,
                    selectedOption = selectedCareer,
                    onOptionSelected = {
                        selectedCareer = it
                        viewModel.career = it
                    },
                    accentColor = turquoise,
                    neonColor = pinkNeon
                )

                Spacer(modifier = Modifier.height(26.dp))

                // Selector de semestre
                Text(
                    "Semestre",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = turquoise,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 4.dp, bottom = 2.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                DropdownMenuField(
                    label = "Selecciona tu semestre",
                    options = semesters,
                    selectedOption = selectedSemester,
                    onOptionSelected = {
                        selectedSemester = it
                        viewModel.semester = it
                    },
                    accentColor = turquoise,
                    neonColor = pinkNeon
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { navController.navigate(route = AppScreens.HobbieScreen.route) },
                    enabled = selectedCareer.isNotEmpty() && selectedSemester.isNotEmpty(),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    accentColor: Color,
    neonColor: Color
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = accentColor,
                unfocusedBorderColor = accentColor.copy(alpha = 0.55f),
                focusedLabelColor = accentColor,
                cursorColor = neonColor
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option, color = accentColor, fontWeight = FontWeight.Medium) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}