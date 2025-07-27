package com.example.finalproject.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finalproject.ui.Session.OnboardingViewModel

@Composable
fun WelcomeScreen(
    navController: NavController,
    viewModel: OnboardingViewModel
) {
    // Colores vibrantes institucionales
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
                containerColor = Color.White.copy(alpha = 0.95f)
            ),
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(28.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Bienvenido a la app",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = pinkNeon
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "Por favor, sigue estas reglas de convivencia:",
                        fontSize = 18.sp,
                        color = darkBg
                    )
                    Spacer(modifier = Modifier.height(28.dp))

                    RuleItem("✅ Sé tú mismo.", "Usa fotos reales y una biografía auténtica.", turquoise)
                    RuleItem("🔒 Mantente seguro.", "No compartas información personal muy pronto.", pinkNeon)
                    RuleItem("😎 Sé respetuoso.", "Trata a los demás como quieres que te traten.", turquoise)
                    RuleItem("⚠️ Sé proactivo.", "Reporta cualquier comportamiento inadecuado.", pinkNeon)
                }
                Spacer(modifier = Modifier.height(38.dp))
                Button(
                    onClick = { navController.navigate("profile") },
                    colors = ButtonDefaults.buttonColors(containerColor = pinkNeon, contentColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("ACEPTO", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
fun RuleItem(title: String, description: String, accentColor: Color) {
    Column(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
    ) {
        Text(title, fontWeight = FontWeight.Bold, color = accentColor, fontSize = 18.sp)
        Text(description, fontSize = 15.sp, color = Color(0xFF23243A))
    }
}