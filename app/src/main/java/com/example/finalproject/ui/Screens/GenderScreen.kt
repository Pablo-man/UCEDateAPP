package com.example.finalproject.ui.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
fun GenderScreen(
    onBack: () -> Unit = {},
    navController: NavController,
    viewModel: OnboardingViewModel
) {
    var selectedGender by remember { mutableStateOf(viewModel.career)}

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
            text = "I am a",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Opciones de género
        Column (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GenderOptionButton(
                text = "Man",
                isSelected = selectedGender == "Man",
                onClick = {
                    selectedGender = "Man"
                    viewModel.gender = "Man"
                }
            )
            GenderOptionButton(
                text = "Woman",
                isSelected = selectedGender == "Woman",
                onClick = {
                    selectedGender = "Woman"
                    viewModel.gender = "Woman"
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Botón CONTINUE
        Button(
            //onClick = { selectedGender?.let { onContinue(it) } },
            onClick = {navController.navigate(route = AppScreens.CareerScreen.route)},
            enabled = selectedGender != null,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("CONTINUE")
        }
    }
}
@Composable
fun GenderOptionButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        border = BorderStroke(
            width = 2.dp,
            color = if (isSelected) Color.Black else Color.LightGray
        )
    ) {
        Text(
            text = text,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}