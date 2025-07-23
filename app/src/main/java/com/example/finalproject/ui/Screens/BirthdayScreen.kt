package com.example.finalproject.ui.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.*
import androidx.navigation.NavBackStackEntry
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Flecha de regreso
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
            text = "Mi\ncumpleaños es",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Campos para año, mes y día
        var year by remember { mutableStateOf(viewModel.birthYear) }
        var month by remember { mutableStateOf(viewModel.birthMonth) }
        var day by remember { mutableStateOf(viewModel.birthDay) }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
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
                modifier = Modifier.weight(1f)
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
                modifier = Modifier.weight(1f)
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
                modifier = Modifier.weight(1f)
            )

        }

        Spacer(modifier = Modifier.height(8.dp))

        // Subtexto
        Text(
            text = "Tu edad será pública",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.weight(1f))

        // Botón continuar (desactivado si campos incompletos)
        val isValid = year.length == 4 && month.length == 2 && day.length == 2

        Button(
            onClick = {navController.navigate(route = AppScreens.GenderScreen.route)},
            enabled = isValid,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("CONTINUAR")
        }
    }
}