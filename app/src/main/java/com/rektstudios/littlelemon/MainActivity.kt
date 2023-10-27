package com.rektstudios.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.rektstudios.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
	private val httpClient = HttpClient(Android) {
		install(ContentNegotiation) {
			json(contentType = ContentType("text", "plain"))
		}
	}

	private val database by lazy {
		Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database").build()
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			LittleLemonTheme {
				val sharedPreferences by lazy {
					getSharedPreferences(
						"MyPrefs",
						MODE_PRIVATE
					)
				}
				NavigationComposable(sharedPreferences, database)
			}
		}
		lifecycleScope.launch(Dispatchers.IO) {
			if (database.menuItemDao().isEmpty()) {
				saveMenuToDatabase(fetchMenu())
			}
		}
	}
	private suspend fun fetchMenu(): List<MenuItemNetwork> {
		val response: MenuNetwork =
			httpClient.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
				.body<MenuNetwork>()

		return response.menu
	}

	private fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>) {
		val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
		database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
	}
}
