package com.adwi.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.domain.WallpaperListState
import com.adwi.components.theme.Dimensions
import com.adwi.components.theme.paddingValues
import com.adwi.core.domain.LoadingState
import com.adwi.domain.Wallpaper
import com.valentinilk.shimmer.shimmer

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun WallpaperListPanel(
    modifier: Modifier = Modifier,
    state: WallpaperListState = WallpaperListState(),
    categoryName: String = "",
    onWallpaperClick: (Int) -> Unit = {},
    onLongPress: (Wallpaper) -> Unit
) {
    Column(modifier = modifier) {
        CategoryTitle(
            name = categoryName,
            modifier = Modifier.padding(horizontal = paddingValues)
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                start = paddingValues,
                end = paddingValues
            ),
            horizontalArrangement = Arrangement.spacedBy(paddingValues)
        ) {
            items(items = state.wallpapers, itemContent = { wallpaper ->
                var isHeartEnabled by remember { mutableStateOf(wallpaper.isFavorite) }
                WallpaperItem(
                    wallpaper = wallpaper,
                    onWallpaperClick = { onWallpaperClick(wallpaper.id) },
                    onLongPress = { item ->
                        isHeartEnabled = !isHeartEnabled
                        onLongPress(item)
                    },
                    isHeartEnabled = isHeartEnabled
                )
            })
        }
        if (state.loadingState is LoadingState.Loading) {
            ShimmerRow()
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
private fun WallpaperItem(
    modifier: Modifier = Modifier,
    elevation: Dp = Dimensions.small,
    shape: Shape = MaterialTheme.shapes.small,
    wallpaper: Wallpaper,
    onWallpaperClick: (Int) -> Unit = {},
    onLongPress: (Wallpaper) -> Unit,
    isHeartEnabled: Boolean
) {
    Card(
        elevation = elevation,
        shape = shape,
        modifier = modifier
            .size(100.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onWallpaperClick(wallpaper.id) },
                    onLongPress = { onLongPress(wallpaper) },
                )
            }
    ) {
        Box() {
            PexCoilImage(
                imageUrl = wallpaper.imageUrlTiny,
                modifier = Modifier.fillMaxSize()
            )
            PexAnimatedHeart(
                state = isHeartEnabled,
                size = 64.dp,
                speed = 1.5f,
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }
    }
}

@Composable
private fun ShimmerRow(
    modifier: Modifier = Modifier,
    elevation: Dp = Dimensions.small,
    shape: Shape = MaterialTheme.shapes.small
) {
    Column(modifier = modifier.fillMaxWidth()) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .shimmer(),
            contentPadding = PaddingValues(start = paddingValues),
            horizontalArrangement = Arrangement.spacedBy(paddingValues / 2)
        ) {
            items(5) {
                Card(
                    elevation = elevation,
                    modifier = modifier
                        .padding(paddingValues / 2)
                        .size(100.dp)
                        .shimmer(),
                    shape = shape,
                    content = {}
                )
            }
        }
    }
}