package com.example.finalproject.ui.navigation

sealed class NavRoutes(val route: String) {
    object Login : NavRoutes("login")
    object Register : NavRoutes("register")
    object Home : NavRoutes("home")

    object Welcome : NavRoutes("welcome")
    object Profile : NavRoutes("profile")

}
