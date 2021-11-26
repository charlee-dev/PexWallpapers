package com.adwi.pexwallpapers.ui.components

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adwi.pexwallpapers.ui.theme.Neutral3
import com.adwi.pexwallpapers.ui.theme.Neutral5
import com.adwi.pexwallpapers.ui.theme.PrimaryShadowDark
import com.adwi.pexwallpapers.ui.theme.PrimaryShadowLight
import me.nikhilchaudhari.library.NeuInsets
import me.nikhilchaudhari.library.neumorphic
import me.nikhilchaudhari.library.shapes.NeuShape
import me.nikhilchaudhari.library.shapes.Pot
import me.nikhilchaudhari.library.shapes.Pressed
import me.nikhilchaudhari.library.shapes.Punched

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.neumorphicPunched(
    neuInsets: NeuInsets = NeuInsets(),
    neuShape: NeuShape = Punched.Rounded(30.dp),
    lightShadowColor: Color = PrimaryShadowLight,
    darkShadowColor: Color = PrimaryShadowDark,
    strokeWidth: Dp = 6.dp,
    elevation: Dp = 6.dp
) = composed {

    val isLightTheme = MaterialTheme.colors.isLight

    val lightLightShadow = if (isLightTheme) lightShadowColor else Neutral3
    val lightDarkShadow = if (isLightTheme) darkShadowColor else Neutral5

    this.neumorphic(
        neuInsets = neuInsets,
        neuShape = neuShape,
        lightShadowColor = lightLightShadow,
        darkShadowColor = lightDarkShadow,
        strokeWidth = strokeWidth,
        elevation = elevation
    )
}

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.neumorphicPresssed(
    neuInsets: NeuInsets = NeuInsets(),
    neuShape: NeuShape = Pressed.Rounded(30.dp),
    lightShadowColor: Color = PrimaryShadowLight,
    darkShadowColor: Color = PrimaryShadowDark,
    strokeWidth: Dp = 6.dp,
    elevation: Dp = 6.dp
) = composed {
    this.neumorphic(
        neuInsets = neuInsets,
        neuShape = neuShape,
        lightShadowColor = lightShadowColor,
        darkShadowColor = darkShadowColor,
        strokeWidth = strokeWidth,
        elevation = elevation
    )
}

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.neumorphicPotShape(
    neuInsets: NeuInsets = NeuInsets(),
    neuShape: NeuShape = Pot.Rounded(30.dp),
    lightShadowColor: Color = PrimaryShadowLight,
    darkShadowColor: Color = PrimaryShadowDark,
    strokeWidth: Dp = 6.dp,
    elevation: Dp = 6.dp
) = composed {
    this.neumorphic(
        neuInsets = neuInsets,
        neuShape = neuShape,
        lightShadowColor = lightShadowColor,
        darkShadowColor = darkShadowColor,
        strokeWidth = strokeWidth,
        elevation = elevation
    )
}