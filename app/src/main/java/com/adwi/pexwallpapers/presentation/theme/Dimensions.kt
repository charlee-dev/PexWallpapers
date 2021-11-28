package com.adwi.pexwallpapers.presentation.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.dp

val paddingValues = Dimensions.extraExtraLarge

object Dimensions {

    val extraSmall = 2.dp
    val small = 4.dp
    val medium = 8.dp
    val large = 12.dp
    val extraLarge = 16.dp
    val extraExtraLarge = 24.dp

    object BottomBar {
        val TextIconSpacing = 2.dp
        val BottomNavHeight = 64.dp
        val BottomNavLabelTransformOrigin = TransformOrigin(0f, 0.5f)
        val BottomNavIndicatorShape = RoundedCornerShape(percent = 50)
        val BottomNavigationItemPadding = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    }
}