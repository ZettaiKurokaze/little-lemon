package com.rektstudios.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.rektstudios.littlelemon.ui.theme.LittleLemonColor


@Composable
fun Home(navController: NavHostController, database: AppDatabase) {
	val databaseMenuItems by database.menuItemDao().getAll().observeAsState(emptyList())
	Column (modifier = Modifier.background(color= Color.White)){
		TopAppBar(true, navController)
		UpperPanel(databaseMenuItems)
	}
}


@Composable
fun TopAppBar(home: Boolean = false, navController: NavHostController? = null) {
	Row(
		horizontalArrangement = if (home) {Arrangement.SpaceBetween} else {Arrangement.Center},
		modifier = Modifier
			.fillMaxWidth()
			.fillMaxHeight(0.08f),
		verticalAlignment = Alignment.CenterVertically
	) {
		if(home) { IconButton(onClick = {}){} }
		Image(
			painter = painterResource(id = R.drawable.littlelemonimgtxt_nobg),
			contentDescription = "Little Lemon Logo",
			modifier = Modifier
				.fillMaxWidth(0.5F)
				.padding(horizontal = 20.dp)
		)
		if (home) {
			IconButton(onClick = {
				navController?.navigate(Profile.route)
			}) {
				Image(
					painter = painterResource(id = R.drawable.ic_user),
					contentDescription = "Cart",
					modifier = Modifier.size(24.dp)
				)
			}
		}
	}
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpperPanel(items: List<MenuItemRoom> = listOf()) {
	var searchPhrase by remember {mutableStateOf("")};
	Column(
		modifier = Modifier
			.background(LittleLemonColor.green)
			.padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 16.dp)
	) {
		Text(
			text = stringResource(id = R.string.title),
			fontSize = 40.sp,
			fontWeight = FontWeight.Bold,
			color = LittleLemonColor.yellow
		)
		Row(
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Column {
				Text(
					text = stringResource(id = R.string.location),
					fontSize = 24.sp,
					color = LittleLemonColor.cloud
				)
				Text(
					text = stringResource(id = R.string.description),
					style = MaterialTheme.typography.bodyLarge,
					color = LittleLemonColor.cloud,
					modifier = Modifier
						.padding(bottom = 28.dp, end = 20.dp, top = 20.dp)
						.fillMaxWidth(0.6f)
				)
			}

			Image(
				painter = painterResource(id = R.drawable.upperpanelimage),
				contentDescription = "Upper Panel Image",
				modifier = Modifier
					.padding(top = 20.dp)
					.clip(RoundedCornerShape(10.dp))
			)
		}
		OutlinedTextField(
			value = searchPhrase,
			shape = RoundedCornerShape(10.dp),
			leadingIcon = {Image(
				painter = painterResource(id = R.drawable.ic_search),
				contentDescription = null,
				alpha = 0.5f
			)},
			placeholder = {
				Text(text = "Enter Search String",
					textAlign = TextAlign.Center,
					modifier = Modifier.fillMaxWidth(0.85f),
					fontSize = 18.sp)
			},
			modifier = Modifier
				.fillMaxWidth()
				.padding(top = 20.dp),
			onValueChange = { searchPhrase = it },
			colors = TextFieldDefaults.textFieldColors(containerColor = LittleLemonColor.cloud)
		)
	}
	val categories = (items.map{it.category}).toSet().toList()
	var menuItems = if (searchPhrase.isNotBlank()) {items.filter { it.title.lowercase()
		.contains(searchPhrase.lowercase()) }}
		else {items}
	val selected = remember { mutableStateListOf<String>() }
	Column (modifier = Modifier.padding(start = 20.dp, end = 20.dp)){
		Card(
			colors = CardDefaults.cardColors(
				containerColor = Color.White,
			),
			modifier = Modifier
				.fillMaxWidth()
		) {
			Text(
				text = stringResource(R.string.order_for_delivery),
				style = MaterialTheme.typography.headlineLarge,
				modifier = Modifier
					.padding(top = 30.dp)
					.background(color = Color.White)
			)
		}
		LazyRow (
			modifier = Modifier
				.fillMaxWidth()
				.padding(bottom = 30.dp, top = 15.dp)
		){
			itemsIndexed(categories){_,category ->
				var clicked by remember { mutableStateOf(false) }
				Card(
					onClick = {
								clicked=!clicked;
							    if(clicked){ selected.add(category)}
								else {selected.remove(category)}
							  },
					colors = CardDefaults.cardColors(containerColor = if(clicked)  { LittleLemonColor.green }
					else {LittleLemonColor.cloud}),
					modifier = Modifier
						.padding(end = 20.dp),
					shape = RoundedCornerShape(30.dp)
				) {
					Text(
						text = category,
						style = MaterialTheme.typography.headlineMedium,
						color = if(clicked)  { LittleLemonColor.cloud }
						else {LittleLemonColor.green},
						modifier = Modifier
							.padding(top=10.dp, bottom=10.dp, start = 20.dp, end = 20.dp)

					)
				}
			}
		}
		Divider(
			thickness = 1.dp,
			color = Color.LightGray
		)
		if (selected.isNotEmpty()) { menuItems= menuItems.filter { selected.contains(it.category)
		} }
		LazyColumn {
			itemsIndexed(menuItems) { _, item ->
				MenuDish(item)
			}
		}
	}
}




@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuDish(item: MenuItemRoom) {
	Card(
		colors = CardDefaults.cardColors(
		containerColor = Color.White)) {
		Row (
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
				.padding(top = 8.dp, bottom = 8.dp)
		){
			Column {
				Text(
					text = item.title,
					style = MaterialTheme.typography.headlineMedium
				)
				Text(
					text = item.description,
					style = MaterialTheme.typography.bodyLarge,
					modifier = Modifier
						.fillMaxWidth(.75f)
						.padding(top = 5.dp, bottom = 5.dp, end = 20.dp)
				)
				Text(
					text = "${item.price}",
					style = MaterialTheme.typography.bodyMedium
				)
			}
			GlideImage(
				model = item.image,
				contentDescription = item.title,
				contentScale = ContentScale.Inside,
				alignment = Alignment.BottomCenter,
				modifier = Modifier
			)
		}
	}
	Divider(
		modifier = Modifier.padding(start = 12.dp, end = 12.dp),
		thickness = 1.dp,
		color = LittleLemonColor.cloud
	)
}
