package com.rektstudios.littlelemon

import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rektstudios.littlelemon.ui.theme.LittleLemonColor


@Composable
fun Onboarding(navController: NavHostController, sharedPreferences: SharedPreferences) {
	Column (
		modifier = Modifier
		.background(color= Color.White)
		.fillMaxSize()
	){
		TopAppBar()
		WelcomePanel()
		FormPanel(navController, sharedPreferences)
	}
}

@Composable
fun WelcomePanel() {
	Box(
		contentAlignment = Alignment.Center,
		modifier = Modifier
			.fillMaxWidth()
			.fillMaxHeight(0.2f)
			.background(color = LittleLemonColor.green)
	) {
		Text(
			text = stringResource(id = R.string.welcome),
			textAlign = TextAlign.Center,
			fontSize = 30.sp,
			fontWeight = FontWeight.Bold,
			color = Color.White,

		)
	}
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormPanel( navController: NavHostController,
                          sharedPreferences: SharedPreferences, login: Boolean = true) {
	var firstName by remember { mutableStateOf("") };
	var lastName by remember { mutableStateOf("") };
	var email by remember { mutableStateOf("") };
	val context = LocalContext.current
	val name = sharedPreferences.getString("firstName", "")
	if(name!!.isNotBlank()){
		firstName = name
		lastName = sharedPreferences.getString("lastName", "")!!
		email = sharedPreferences.getString("email", "")!!
	}
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding( start = 20.dp, end = 20.dp )
	){
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.weight(1f, true),
			verticalArrangement = Arrangement.Center
		) {
			Text(
				text = "Personal Information",
				style = MaterialTheme.typography.headlineMedium
			)
			Text(
				text = "First Name",
				style = MaterialTheme.typography.labelMedium,
				modifier = Modifier.padding(top = 50.dp, bottom = 5.dp)
			)
			OutlinedTextField(
				value = firstName,
				onValueChange = { firstName = it },
				shape = RoundedCornerShape(10.dp),
				modifier = Modifier
					.fillMaxWidth(),
			)
			Text(
				text = "Last Name",
				style = MaterialTheme.typography.labelMedium,
				modifier = Modifier.padding(top = 30.dp, bottom = 5.dp)
			)
			OutlinedTextField(
				value = lastName,
				onValueChange = { lastName = it },
				shape = RoundedCornerShape(10.dp),
				modifier = Modifier
					.fillMaxWidth(),
			)
			Text(
				text = "Email",
				style = MaterialTheme.typography.labelMedium,
				modifier = Modifier.padding(top = 30.dp, bottom = 5.dp)
			)
			OutlinedTextField(
				value = email,
				onValueChange = { email = it },
				shape = RoundedCornerShape(10.dp),
				modifier = Modifier
					.fillMaxWidth(),
			)
		}

		Button(
			onClick = {
						if(login) {
							if(firstName.isBlank() || lastName.isBlank() || email.isBlank()){
								Toast.makeText(context, "Registration unsuccessful. Please enter " +
										"all data.", Toast.LENGTH_SHORT).show()
							}
							else {
								Toast.makeText(context, "Registration successful!", Toast
									.LENGTH_SHORT).show()
								sharedPreferences.edit()
									.putString("firstName", firstName)
									.putString("lastName", lastName)
									.putString("email", email)
									.apply()
								navController.navigate(Home.route)
							}
						}
						else {
							sharedPreferences.edit().clear().apply()
							navController.navigate(Onboarding.route)
						}
					  },
			shape= RoundedCornerShape(10.dp),
			modifier = Modifier
				.padding(bottom = 40.dp)
				.fillMaxWidth()
				.height(50.dp)
		) {
			Text(
				text = if (login) {
					"Register"
				} else {
					"Log out"
				},
				style = MaterialTheme.typography.headlineMedium,
				color = LittleLemonColor.charcoal
			)
		}
	}
}

