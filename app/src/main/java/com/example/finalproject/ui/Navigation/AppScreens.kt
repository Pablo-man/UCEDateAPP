package com.example.finalproject.ui.Navigation

sealed class AppScreens(val route: String) {
    object BirthdayScreen: AppScreens("birthday_screen")
    object CareerScreen: AppScreens("career_screen")
    object GenderScreen: AppScreens("gender_screen")
    object NameScreen: AppScreens("name_screen")
    object StateScreen: AppScreens("state_screen")
}