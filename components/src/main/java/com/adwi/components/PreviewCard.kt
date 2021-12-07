package com.adwi.components

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
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues
import com.adwi.pexwallpapers.domain.model.Wallpaper

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun PreviewCard(
    modifier: Modifier = Modifier,
    wallpaper: Wallpaper,
    shape: Shape = MaterialTheme.shapes.large,
    showShadows: Boolean
) {
    Card(
        shape = shape,
        modifier = modifier
            .fillMaxSize()
            .neumorphicShadow(enabled = showShadows)
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
            PreviewCard(
                wallpaper = Wallpaper.defaultDaily,
                showShadows = true
            )
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
            PreviewCard(
                wallpaper = Wallpaper.defaultDaily,
                showShadows = true
            )
        }
    }
}