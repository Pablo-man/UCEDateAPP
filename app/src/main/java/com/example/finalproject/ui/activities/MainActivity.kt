package com.example.finalproject.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.example.finalproject.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Mostrar un mensaje de bienvenida simple
        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        tvWelcome.text = "¡Bienvenido a finalproject!"
    }
}
