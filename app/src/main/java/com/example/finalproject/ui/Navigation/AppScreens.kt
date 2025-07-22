package com.example.finalproject.ui.Navigation

sealed class AppScreens(val route: String) {
    object LoginScreen : AppScreens("login_screen")
    object RegisterScreen : AppScreens("register_screen")
    object HomeScreen : AppScreens("home_screen")
    object BirthdayScreen: AppScreens("birthday_screen")
    object CareerScreen: AppScreens("career_screen")
    object GenderScreen: AppScreens("gender_screen")
    object NameScreen: AppScreens("name_screen")
    object StateScreen: AppScreens("state_screen")
    object Welcome : AppScreens("welcome")
    object Profile : AppScreens("profile")
}