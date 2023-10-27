package com.rektstudios.littlelemon

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationComposable(sharedPreferences: SharedPreferences, database: AppDatabase){
    val navController = rememberNavController()
    val firstName = sharedPreferences.getString("firstName", "")
    NavHost(navController = navController, startDestination = if(firstName!!.isBlank()) {Onboarding
        .route} else {Home.route}) {
        composable(Home.route) {
            Home(navController, database)
        }
        composable(Onboarding.route) {
            Onboarding(navController, sharedPreferences)
        }
        composable(Profile.route) {
            Profile(navController, sharedPreferences)
        }
    }
}
