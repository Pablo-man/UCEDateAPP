package com.example.finalproject.ui.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.finalproject.ui.Navigation.AppScreens

@Composable
fun WelcomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {

            Spacer(modifier = Modifier.height(16.dp))
            Text("Bienvenido a la app", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Por favor, sigue estas reglas de convivencia:")

            Spacer(modifier = Modifier.height(16.dp))

            RuleItem("✅ Sé tú mismo.", "Usa fotos reales y una biografía auténtica.")
            RuleItem("🔒 Mantente seguro.", "No compartas información personal muy pronto.")
            RuleItem("😎 Sé respetuoso.", "Trata a los demás como quieres que te traten.")
            RuleItem("⚠️ Sé proactivo.", "Reporta cualquier comportamiento inadecuado.")
        }
        Button(
            onClick = { navController.navigate("profile") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF95A4E)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("ACEPTO")
        }
    }
}

@Composable
fun RuleItem(title: String, description: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(title, fontWeight = FontWeight.Bold)
        Text(description, fontSize = 14.sp, color = Color.Gray)
    }
}
