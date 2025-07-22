package com.example.finalproject.ui.navigation

import com.example.finalproject.ui.Principal.ProfileScreen
import com.example.finalproject.ui.Principal.WelcomeScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavRoutes.Login.route) {
        composable(NavRoutes.Login.route) {
            LoginScreen(navController)
        }
        composable(NavRoutes.Register.route) {
            RegisterScreen(navController)
        }
        composable(NavRoutes.Home.route) {
            HomeScreen()
        }
        composable(NavRoutes.Welcome.route) {
            WelcomeScreen(navController)
        }
        composable(NavRoutes.Profile.route) {
            ProfileScreen(navController)
        }

    }
}
