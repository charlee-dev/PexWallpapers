package com.adwi.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.theme.Dimensions
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues
import com.adwi.composables.R
import com.adwi.core.domain.Resource
import com.adwi.domain.Wallpaper

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun WallpaperListHorizontalPanel(
    modifier: Modifier = Modifier,
    wallpapers: Resource<List<Wallpaper>>?,
    categoryName: String = "",
    onWallpaperClick: (Int) -> Unit,
    onShowMoreClick: () -> Unit,
    onLongPress: (Wallpaper) -> Unit
) {
    wallpapers?.let { resource ->
        Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            CategoryPanel(
                categoryName = categoryName,
                onShowMoreClick = onShowMoreClick,
                showMore = true,
                modifier = Modifier.padding(horizontal = paddingValues)
            )
            ShimmerRow(
                visible = resource.data.isNullOrEmpty() && resource.error == null
            )
            ShimmerErrorMessage(
                visible = resource.data.isNullOrEmpty() && resource.error != null,
                message = stringResource(
                    id = R.string.could_not_refresh,
                    resource.error?.localizedMessage
                        ?: stringResource(R.string.unknown_error_occurred)
                ),
                modifier = Modifier.padding(horizontal = paddingValues)
            )
            resource.data?.let { list ->
                WallpaperListHorizontal(
                    onWallpaperClick = onWallpaperClick,
                    onLongPress = onLongPress,
                    wallpapers = list
                )
            }
        }
    }
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
private fun WallpaperListHorizontal(
    modifier: Modifier = Modifier,
    wallpapers: List<Wallpaper>,
    onWallpaperClick: (Int) -> Unit,
    onLongPress: (Wallpaper) -> Unit
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
            val isHeartEnabled by remember { mutableStateOf(wallpaper.isFavorite) }

            WallpaperItem(
                wallpaper = wallpaper,
                onWallpaperClick = { onWallpaperClick(wallpaper.id) },
                onLongPress = { onLongPress(wallpaper) },
                isHeartEnabled = isHeartEnabled,
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
    onWallpaperClick: () -> Unit,
    onLongPress: () -> Unit,
    isHeartEnabled: Boolean,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            elevation = elevation,
            shape = shape,
            modifier = modifier
                .size(100.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = { onLongPress() },
                        onTap = { onWallpaperClick() }
                    )
                }
        ) {
            Box {
                PexCoilImage(
                    imageUrl = wallpaper.imageUrlTiny,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
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
@Preview(showBackground = true)
@Composable
private fun WallpaperListPanelPreview() {
    PexWallpapersTheme {
        WallpaperItem(
            onLongPress = {},
            onWallpaperClick = {},
            wallpaper = Wallpaper.defaultDaily,
            isHeartEnabled = true
        )
    }
}