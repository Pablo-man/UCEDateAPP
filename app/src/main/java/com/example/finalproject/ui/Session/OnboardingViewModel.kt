package com.example.finalproject.ui.Session

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class OnboardingViewModel : ViewModel() {
    var name by mutableStateOf("")

    // Puedes agregar más campos según tu flujo de onboarding

    fun clearData() {
        name = ""
    }
}
