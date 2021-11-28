package com.adwi.pexwallpapers.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import coil.annotation.ExperimentalCoilApi
import com.adwi.pexwallpapers.domain.model.Wallpaper
import com.adwi.pexwallpapers.presentation.theme.PexWallpapersTheme
import com.adwi.pexwallpapers.presentation.theme.paddingValues

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun PreviewCard(
    modifier: Modifier = Modifier,
    wallpaper: Wallpaper,
    shape: Shape = MaterialTheme.shapes.large,
) {
    Card(
        shape = shape,
        modifier = modifier
            .fillMaxSize()
            .neumorphicShadow()
    ) {
        PexCoilImage(
            imageUrl = wallpaper.imageUrlPortrait,
            wallpaperId = wallpaper.id,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Preview(showBackground = true, name = "Light")
@Composable
private fun PreviewCardPreviewLight() {
    PexWallpapersTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            PreviewCard(wallpaper = Wallpaper.defaultDaily)
        }
    }
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Preview(showBackground = true, name = "Dark")
@Composable
private fun PreviewCardPreviewDark() {
    PexWallpapersTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            PreviewCard(wallpaper = Wallpaper.defaultDaily)
        }
    }
}