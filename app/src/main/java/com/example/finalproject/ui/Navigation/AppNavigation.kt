package com.example.finalproject.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.ui.Screens.BirthdayScreen
import com.example.finalproject.ui.Screens.CareerScreen
import com.example.finalproject.ui.Screens.GenderScreen

import com.example.finalproject.ui.Screens.LoginScreen
import com.example.finalproject.ui.Screens.NameScreen
import com.example.finalproject.ui.Screens.ProfileScreen
import com.example.finalproject.ui.Screens.RegisterScreen
import com.example.finalproject.ui.Screens.StateScreen
import com.example.finalproject.ui.Screens.WelcomeScreen

@Composable
fun AppNavigation(navController: NavHostController){
    val navController = rememberNavController()
    NavHost(navController= navController, startDestination = AppScreens.LoginScreen.route ){
        composable(AppScreens.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(AppScreens.RegisterScreen.route) {
            RegisterScreen(navController)
        }

        composable(route= AppScreens.NameScreen.route){
            NameScreen({}, navController)
        }
        composable(route= AppScreens.BirthdayScreen.route){
            BirthdayScreen({}, navController)
        }
        composable(route= AppScreens.CareerScreen.route){
            CareerScreen({}, navController)
        }
        composable(route= AppScreens.GenderScreen.route){
            GenderScreen({},navController)
        }
        composable(route= AppScreens.StateScreen.route){
            StateScreen(navController)
        }
        composable(AppScreens.Welcome.route) {
            WelcomeScreen(navController)
        }
        composable(AppScreens.Profile.route) {
            ProfileScreen(navController)
        }
    }
}