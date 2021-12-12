package com.adwi.feature_home.presentation.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
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
    onLongPress: (Wallpaper) -> Unit,
    showShadows: Boolean,
    lowRes: Boolean
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        ShimmerRow(
            visible = wallpapers.data.isNullOrEmpty() && wallpapers.error == null,
            showShadows = showShadows
        )
        ShimmerErrorMessage(
            visible = wallpapers.data.isNullOrEmpty() && wallpapers.error != null,
            message = wallpapers.error?.localizedMessage
                ?: "Unknown error occurred",
            modifier = Modifier.padding(horizontal = paddingValues),
            showShadows = showShadows
        )
        wallpapers.data?.let { list ->
            StaggeredVerticalGrid(
                modifier = Modifier.padding(paddingValues / 2)
            ) {
                val heights = listOf(415, 315, 375, 213, 275, 290)
                list.forEach { wallpaper ->
                    val height by remember { mutableStateOf(heights.random().dp) }
                    WallpaperItem(
                        wallpaper = wallpaper,
                        onWallpaperClick = { onWallpaperClick(wallpaper.id) },
                        onLongPress = { onLongPress(wallpaper) },
                        isHeartEnabled = wallpaper.isFavorite,
                        showShadows = showShadows,
                        lowRes = lowRes,
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
    showShadows: Boolean = true,
    lowRes: Boolean = false
) {
    var isPressed by remember { mutableStateOf(false) }
    val pressed = updateTransition(targetState = isPressed, label = "Press")

    val scale by pressed.animateFloat(
        label = "Scale",
        transitionSpec = { tween(400) }
    ) {
        if (it) .98f else 1f
    }

    Column(
        modifier = modifier.neumorphicShadow(
            enabled = showShadows,
            offset = (-5).dp
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            shape = shape,
            backgroundColor = MaterialTheme.colors.primary,
            modifier = modifier
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { onWallpaperClick() },
                        onLongPress = { onLongPress() },
                        onPress = {
                            isPressed = true
                            this.tryAwaitRelease()
                            isPressed = false
                        }
                    )
                }
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
        ) {
            Box {
                PexCoilImage(
                    imageUrl = if (lowRes)
                        wallpaper.imageUrlTiny else wallpaper.imageUrlPortrait,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    wallpaperId = wallpaper.id,
                    contentScale = ContentScale.Crop
                )
                PexAnimatedHeart(
                    isFavorite = isHeartEnabled,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(paddingValues / 2)
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