package com.example.finalproject.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.ui.Screens.BirthdayScreen
import com.example.finalproject.ui.Screens.CareerScreen
import com.example.finalproject.ui.Screens.GenderScreen
import com.example.finalproject.ui.Screens.NameScreen
import com.example.finalproject.ui.Screens.StateScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController= navController, startDestination = AppScreens.NameScreen.route ){
        composable(route= AppScreens.NameScreen.route){
            NameScreen({}, {}, navController)
        }
        composable(route= AppScreens.BirthdayScreen.route){
            BirthdayScreen({}, {}, navController)
        }
        composable(route= AppScreens.CareerScreen.route){
            CareerScreen({}, {_, _ -> }, navController)
        }
        composable(route= AppScreens.GenderScreen.route){
            GenderScreen({},{},navController)
        }
        composable(route= AppScreens.StateScreen.route){
            StateScreen(navController)
        }
    }
}