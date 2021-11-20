package com.adwi.components.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = PrimaryDarker,
    primaryVariant = PrimaryDark,
    secondary = AccentLightBlue
)

private val LightColorPalette = lightColors(
    primary = AccentDark,
    primaryVariant = AccentLightBlue,
    secondary = PrimaryDark,
    secondaryVariant = PrimaryDarker,
    background = PrimaryMedium,
    onBackground = AccentDark,
    surface = PrimaryLight,
    onSurface = AccentDark
)

@Composable
fun PexWallpapersTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}