package com.adwi.pexwallpapers.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(percent = 15),
    medium = RoundedCornerShape(10.dp),
    large = RoundedCornerShape(30.dp)
)

val RounderTopShape = RoundedCornerShape(topStartPercent = 100, topEndPercent = 100)