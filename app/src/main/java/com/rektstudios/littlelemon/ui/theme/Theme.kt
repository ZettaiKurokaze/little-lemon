package com.rektstudios.littlelemon.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple700 = Color(0xFFEE9972)

private val LightColorPalette = lightColorScheme(
	primary = LittleLemonColor.yellow,
	secondary = LittleLemonColor.pink
)

@Composable
fun LittleLemonTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
	MaterialTheme(
		colorScheme = LightColorPalette,
		typography = Typography,
		shapes = Shapes,
		content = content
	)
}