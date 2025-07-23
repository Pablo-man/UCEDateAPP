package com.example.finalproject.ui.Session

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class OnboardingViewModel : ViewModel() {
    var uid by mutableStateOf("")
    var name: String by mutableStateOf("")
    var career by mutableStateOf("")
    var semester by mutableStateOf("")
    var gender by mutableStateOf("")
    var state by mutableStateOf("")
    var email by mutableStateOf("")
    var photo by mutableStateOf("")
    var pref by mutableStateOf("")
    var birthYear by mutableStateOf("")
    var birthMonth by mutableStateOf("")
    var birthDay by mutableStateOf("")
    var hobbies by mutableStateOf(listOf<String>())
    var birthDate: String = ""
        get() = "$birthYear-$birthMonth-$birthDay"

    // Puedes agregar más campos según tu flujo de onboarding

    fun clearData() {
        uid = ""
        name = ""
        birthDate = ""
        career = ""
        semester = ""
        gender = ""
        state = ""
        email = ""
        photo = ""
        pref = ""
        hobbies = listOf()
    }
}
