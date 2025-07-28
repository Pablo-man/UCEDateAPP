package com.example.finalproject.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.ui.Screens.BirthdayScreen
import com.example.finalproject.ui.Screens.CareerScreen
import com.example.finalproject.ui.Screens.ChatScreen
import com.example.finalproject.ui.Screens.GenderScreen

import com.example.finalproject.ui.Screens.LoginScreen
import com.example.finalproject.ui.Screens.NameScreen
import com.example.finalproject.ui.Screens.ProfileScreen
import com.example.finalproject.ui.Screens.RegisterScreen
import com.example.finalproject.ui.Screens.HobbiesScreen
import com.example.finalproject.ui.Screens.MatchScreen
import com.example.finalproject.ui.Screens.PublicProfileScreen
import com.example.finalproject.ui.Screens.WelcomeScreen
import com.example.finalproject.ui.Session.OnboardingViewModel

@Composable
fun AppNavigation(navController: NavHostController){

    val navController = rememberNavController()
    val onboardingViewModel: OnboardingViewModel = viewModel()



    NavHost(navController= navController, startDestination = AppScreens.LoginScreen.route, route = "onboarding_root" ){
        composable(AppScreens.LoginScreen.route) {
            LoginScreen(navController, onboardingViewModel)
        }
        composable(AppScreens.RegisterScreen.route) {
            RegisterScreen(navController)
        }

        composable(route= AppScreens.NameScreen.route){
            NameScreen({}, navController, onboardingViewModel)
        }
        composable(route= AppScreens.BirthdayScreen.route){
            BirthdayScreen({}, navController, onboardingViewModel)
        }
        composable(route= AppScreens.CareerScreen.route){
            CareerScreen({}, navController, onboardingViewModel)
        }
        composable(route= AppScreens.GenderScreen.route){
            GenderScreen({},navController, onboardingViewModel)
        }
        composable(route= AppScreens.HobbieScreen.route){
            HobbiesScreen(navController, onboardingViewModel)
        }
        composable(AppScreens.Welcome.route) {
            WelcomeScreen(navController, onboardingViewModel)
        }
        composable(AppScreens.Profile.route) {
            ProfileScreen(navController, onboardingViewModel)
        }
        composable("match_screen") {
            MatchScreen(navController = navController, viewModel = onboardingViewModel)
        }
        composable("chat/{receiverName}/{receiverUid}") { backStackEntry ->
            val receiverName = backStackEntry.arguments?.getString("receiverName") ?: ""
            val receiverUid = backStackEntry.arguments?.getString("receiverUid") ?: ""
            ChatScreen(navController, viewModel(), receiverName, receiverUid)
        }
        composable("public_profile/{uid}") { backStackEntry ->
            val userUid = backStackEntry.arguments?.getString("uid") ?: ""
            PublicProfileScreen(navController, userUid)
        }




    }
}