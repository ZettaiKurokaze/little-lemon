package com.rektstudios.littlelemon

import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

@Composable
fun Profile(navController: NavHostController, sharedPreferences: SharedPreferences) {
	Column (
		modifier = Modifier
			.background(color= Color.White)
			.fillMaxSize()
	){
		TopAppBar()
		FormPanel( navController, sharedPreferences, false)
	}
}