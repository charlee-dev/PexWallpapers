package com.adwi.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.domain.WallpaperState
import com.adwi.components.theme.PrimaryDark
import com.adwi.components.theme.paddingValues
import com.adwi.composables.R

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun DailyWallpaper(
    modifier: Modifier = Modifier,
    state: WallpaperState = WallpaperState(),
    placeholder: Int = R.drawable.daily_picture,
    elevation: Dp = 10.dp,
    shape: Shape = MaterialTheme.shapes.large,
    onWallpaperClick: (Int) -> Unit
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val width = this.maxWidth
        Card(
            onClick = { onWallpaperClick(state.wallpaper.id) },
            elevation = elevation,
            shape = shape,
            modifier = Modifier
                .fillMaxWidth()
                .height(width)
                .align(Alignment.Center)
        ) {
            Box {
                PexCoilImage(
                    imageUrl = state.wallpaper.imageUrlLandscape,
                    placeholder = placeholder,
                    modifier = Modifier.align(Alignment.Center)
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
            state = WallpaperState(),
            onWallpaperClick = {}
        )
    }
}