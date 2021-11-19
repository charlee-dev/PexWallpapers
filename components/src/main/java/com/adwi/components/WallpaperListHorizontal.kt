package com.adwi.components

import androidx.compose.animation.animateContentSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.theme.Dimensions
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues
import com.adwi.domain.Wallpaper
import com.valentinilk.shimmer.shimmer
import timber.log.Timber

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun WallpaperListHorizontalPanel(
    modifier: Modifier = Modifier,
    wallpapers: List<Wallpaper>,
    categoryName: String = "",
    onWallpaperClick: (Int) -> Unit,
    onShowMoreClick: () -> Unit,
    onLongPress: (Wallpaper) -> Unit
) {
    Column(modifier = modifier) {
        CategoryPanel(
            categoryName = categoryName,
            onShowMoreClick = onShowMoreClick,
            showMore = true,
            modifier = Modifier.padding(horizontal = paddingValues)
        )
        if (wallpapers.isEmpty()) {
            ShimmerRow()
        } else {
            WallpaperListHorizontal(
                onWallpaperClick = onWallpaperClick,
                onLongPress = onLongPress,
                wallpapers = wallpapers
            )
        }
    }
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
private fun WallpaperListHorizontal(
    modifier: Modifier = Modifier,
    onWallpaperClick: (Int) -> Unit,
    onLongPress: (Wallpaper) -> Unit,
    wallpapers: List<Wallpaper>,
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(
            start = paddingValues,
            end = paddingValues
        ),
        horizontalArrangement = Arrangement.spacedBy(paddingValues)
    ) {
        items(items = wallpapers, itemContent = { wallpaper ->
            var expanded by remember { mutableStateOf(false) }
            val isHeartEnabled by remember { mutableStateOf(wallpaper.isFavorite) }

            WallpaperItem(
                wallpaper = wallpaper,
                onWallpaperClick = { onWallpaperClick(wallpaper.id) },
                onLongPress = {
//                    isHeartEnabled = !isHeartEnabled
                    expanded = !expanded
//                    onLongPress(wallpaper)
                },
                isHeartEnabled = isHeartEnabled,
                expanded = expanded,
                onFavoriteClick = { onLongPress(wallpaper) }
            )
        })
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
    onWallpaperClick: () -> Unit = {},
    onLongPress: () -> Unit,
    isHeartEnabled: Boolean,
    onFavoriteClick: () -> Unit,
    expanded: Boolean
) {
    Card(
        elevation = elevation,
        shape = shape,
        modifier = modifier

            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        Timber
                            .tag("Item")
                            .d("card tap")
                        onFavoriteClick()
                    },
                )
            }
            .animateContentSize()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            PexCoilImage(
                imageUrl = wallpaper.imageUrlTiny,
                modifier = Modifier
                    .size(100.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                Timber
                                    .tag("Item")
                                    .d("image tap")
                                onWallpaperClick()
                            },
                            onLongPress = {
                                Timber
                                    .tag("Item")
                                    .d("image long")
                                onLongPress()
                            },
                        )
                    }
            )
            if (expanded) {
                PexAnimatedHeart(
                    state = isHeartEnabled,
                    size = 64.dp,
                    speed = 1.5f,
                    modifier = Modifier
                )
            }
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
            modifier = modifier
                .fillMaxWidth()
                .shimmer(),
            contentPadding = PaddingValues(
                start = paddingValues,
                end = paddingValues
            ),
            horizontalArrangement = Arrangement.spacedBy(paddingValues)
        ) {
            items(5) {
                Card(
                    elevation = elevation,
                    modifier = modifier
                        .size(100.dp)
                        .shimmer(),
                    shape = shape,
                    content = {}
                )
            }
        }
    }
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
private fun WallpaperListPanelPreview() {
    PexWallpapersTheme {
        WallpaperItem(
            onLongPress = {},
            onWallpaperClick = {},
            wallpaper = Wallpaper.defaultDaily,
            isHeartEnabled = true,
            expanded = true,
            onFavoriteClick = {}
        )
    }
}