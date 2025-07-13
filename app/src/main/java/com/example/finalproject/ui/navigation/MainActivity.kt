package com.example.finalproject.ui.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.ui.theme.FinalProjectTheme
import androidx.compose.ui.platform.LocalInspectionMode


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinalProjectTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    // ✅ Solo crea el NavController si NO estás en modo preview
                    if (!LocalInspectionMode.current) {
                        val navController = rememberNavController()
                        AppNavGraph(navController)
                    } else {
                        // Muestra un dummy en previews
                        Text("Vista previa")
                    }
                }
            }
        }
    }
}
