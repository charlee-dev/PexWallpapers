package com.adwi.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.theme.PrimaryDark
import com.adwi.components.theme.paddingValues
import com.adwi.composables.R
import com.adwi.domain.Wallpaper

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun DailyWallpaper(
    modifier: Modifier = Modifier,
    wallpaper: Wallpaper,
    placeholder: Int = R.drawable.daily_picture,
    elevation: Dp = 10.dp,
    shape: Shape = MaterialTheme.shapes.large,
    onWallpaperClick: (Int) -> Unit,
    onLongPress: (Int) -> Unit
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val width = this.maxWidth

        Card(
            elevation = elevation,
            shape = shape,
            modifier = Modifier
                .fillMaxWidth()
                .height(width)
                .align(Alignment.Center)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = { onLongPress(wallpaper.id) },
                        onTap = { onWallpaperClick(wallpaper.id) },
                    )
                }
        ) {
            Box {
                PexCoilImage(
                    imageUrl = wallpaper.imageUrlPortrait,
                    placeholder = placeholder,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize()
                )
                PexAnimatedHeart(
                    state = wallpaper.isFavorite,
                    size = 128.dp,
                    speed = 1.5f,
                    modifier = Modifier.align(Alignment.TopEnd)
                )
                Card(
                    modifier = Modifier
                        .padding(all = paddingValues)
                        .fillMaxSize(.5f)
                        .align(Alignment.BottomStart),
                    shape = shape
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(PrimaryDark)
                    ) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                        ) {
                            Text(
                                text = "Daily",
                                fontSize = 24.sp
                            )
                            Text(
                                text = "Wallpaper",
                                fontSize = 24.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
private fun DailyWallpaperPreview() {
    MaterialTheme() {
        DailyWallpaper(
            wallpaper = Wallpaper(),
            onWallpaperClick = {},
            onLongPress = {}
        )
    }
}