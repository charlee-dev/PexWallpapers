package com.adwi.pexwallpapers.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.adwi.pexwallpapers.model.Wallpaper

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun PreviewCard(
    modifier: Modifier = Modifier,
    wallpaper: Wallpaper,
    elevation: Dp = 10.dp,
    shape: Shape = MaterialTheme.shapes.large,
) {
    Card(
//        elevation = elevation,
        shape = shape,
        modifier = modifier
            .fillMaxSize()
            .neumorphicPunched()
    ) {
        PexCoilImage(
            imageUrl = wallpaper.imageUrlPortrait,
            wallpaperId = wallpaper.id,
            modifier = Modifier.fillMaxSize()
        )
    }
}