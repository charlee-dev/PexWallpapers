package com.adwi.feature_home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.PexAnimatedHeart
import com.adwi.components.PexCoilImage
import com.adwi.components.StaggeredVerticalGrid
import com.adwi.components.neumorphicShadow
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues
import com.adwi.core.DataState
import com.adwi.pexwallpapers.domain.model.Wallpaper

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun WallpaperListVerticalPanel(
    modifier: Modifier = Modifier,
    wallpapers: DataState<List<Wallpaper>>,
    onWallpaperClick: (Int) -> Unit,
    onLongPress: (Wallpaper) -> Unit
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        ShimmerRow(
            visible = wallpapers.data.isNullOrEmpty() && wallpapers.error == null
        )
        ShimmerErrorMessage(
            visible = wallpapers.data.isNullOrEmpty() && wallpapers.error != null,
            message = wallpapers.error?.localizedMessage
                ?: "Unknown error occurred",
            modifier = Modifier.padding(horizontal = paddingValues)
        )
        wallpapers.data?.let { list ->
            StaggeredVerticalGrid(
                modifier = Modifier.padding(paddingValues / 2)
            ) {
                val heights = listOf(415, 460, 375, 213, 515, 290)
                list.forEach { wallpaper ->
                    val height by remember { mutableStateOf(heights.random().dp) }
                    WallpaperItem(
                        wallpaper = wallpaper,
                        onWallpaperClick = { onWallpaperClick(wallpaper.id) },
                        onLongPress = { onLongPress(wallpaper) },
                        isHeartEnabled = wallpaper.isFavorite,
                        modifier = Modifier
                            .height(height)
                            .fillMaxWidth()
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun WallpaperItem(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    wallpaper: Wallpaper,
    onWallpaperClick: () -> Unit,
    onLongPress: () -> Unit,
    isHeartEnabled: Boolean,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Column(
        modifier = modifier.neumorphicShadow(
            offset = (-3).dp,
            isPressed = isPressed
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            onClick = { onWallpaperClick() },
            shape = shape,
            backgroundColor = MaterialTheme.colors.primary,
            modifier = modifier
                .size(100.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = { onLongPress() }
                    )
                },
            interactionSource = interactionSource
        ) {
            Box {
                PexCoilImage(
                    imageUrl = wallpaper.imageUrlPortrait,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    wallpaperId = wallpaper.id,
                    contentScale = ContentScale.Crop
                )
                PexAnimatedHeart(
                    state = isHeartEnabled,
                    size = 64.dp,
                    speed = 1.5f,
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }
        }
        Spacer(modifier = Modifier.size(paddingValues / 4))
    }
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Preview(showBackground = true, name = "Light")
@Composable
private fun WallpaperListPanelPreviewLight() {
    PexWallpapersTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            WallpaperItem(
                onLongPress = {},
                onWallpaperClick = {},
                wallpaper = Wallpaper.defaultDaily,
                isHeartEnabled = true
            )
        }
    }
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Preview(showBackground = true, name = "Dark")
@Composable
private fun WallpaperListPanelPreviewDark() {
    PexWallpapersTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            WallpaperItem(
                onLongPress = {},
                onWallpaperClick = {},
                wallpaper = Wallpaper.defaultDaily,
                isHeartEnabled = true
            )
        }
    }
}