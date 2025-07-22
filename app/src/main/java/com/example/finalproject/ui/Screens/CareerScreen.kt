package com.example.finalproject.ui.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finalproject.ui.Navigation.AppScreens
import com.example.finalproject.ui.Session.OnboardingViewModel

@Composable
fun CareerScreen(
    onBack: () -> Unit = {},
    navController: NavController,
    viewModel: OnboardingViewModel = viewModel()

) {
    var selectedCareer by remember { mutableStateOf(viewModel.career) }
    var selectedSemester by remember { mutableStateOf(viewModel.semester) }

    val careers = listOf("Ingeniería de Software", "Medicina", "Derecho", "Arquitectura")
    val semesters = (1..10).map { it.toString() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        // Flecha atrás
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopStart
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Título
        Text(
            text = "Soy \nestudiante de",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text("Carrera", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        DropdownMenuField(
            label = "Selecciona tu carrera",
            options = careers,
            selectedOption = selectedCareer,
            onOptionSelected = {
                selectedCareer = it
                viewModel.career = it
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Semestre", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        DropdownMenuField(
            label = "Selecciona tu semestre",
            options = semesters,
            selectedOption = selectedSemester,
            onOptionSelected = {
                selectedSemester = it
                viewModel.semester = it
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {navController.navigate(route = AppScreens.StateScreen.route)},
            enabled = selectedCareer.isNotEmpty() && selectedSemester.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("CONTINUE")
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
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
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}