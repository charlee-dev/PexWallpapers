package com.adwi.pexwallpapers.ui.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.adwi.pexwallpapers.R
import com.adwi.pexwallpapers.model.Wallpaper
import com.adwi.pexwallpapers.model.state.DataState
import com.adwi.pexwallpapers.ui.theme.Dimensions
import com.adwi.pexwallpapers.ui.theme.PexWallpapersTheme
import com.adwi.pexwallpapers.ui.theme.paddingValues

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun WallpaperListHorizontalPanel(
    modifier: Modifier = Modifier,
    wallpapers: DataState<List<Wallpaper>>,
    listState: LazyListState = rememberLazyListState(),
    panelName: String = "",
    onWallpaperClick: (Int) -> Unit,
    onShowMoreClick: () -> Unit,
    onLongPress: (Wallpaper) -> Unit
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        CategoryPanel(
            categoryName = panelName,
            onShowMoreClick = onShowMoreClick,
            showMore = true,
            modifier = Modifier.padding(horizontal = paddingValues)
        )
        ShimmerRow(
            visible = wallpapers.data.isNullOrEmpty() && wallpapers.error == null
        )
        ShimmerErrorMessage(
            visible = wallpapers.data.isNullOrEmpty() && wallpapers.error != null,
            message = stringResource(
                id = R.string.could_not_refresh,
                wallpapers.error?.localizedMessage
                    ?: stringResource(R.string.unknown_error_occurred)
            ),
            modifier = Modifier.padding(horizontal = paddingValues)
        )
        wallpapers.data?.let { list ->
            WallpaperListHorizontal(
                listState = listState,
                onWallpaperClick = onWallpaperClick,
                onLongPress = onLongPress,
                wallpapers = list
            )
        }
    }
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
private fun WallpaperListHorizontal(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    wallpapers: List<Wallpaper>,
    onWallpaperClick: (Int) -> Unit,
    onLongPress: (Wallpaper) -> Unit
) {
    LazyRow(
        state = listState,
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(
            start = paddingValues,
            end = paddingValues
        ),
        horizontalArrangement = Arrangement.spacedBy(paddingValues)
    ) {
        items(items = wallpapers, itemContent = { wallpaper ->

            WallpaperItem(
                wallpaper = wallpaper,
                onWallpaperClick = { onWallpaperClick(wallpaper.id) },
                onLongPress = { onLongPress(wallpaper) },
                isHeartEnabled = wallpaper.isFavorite,
                modifier = Modifier.coloredShadow(cornerRadius = 10.dp)
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
//            elevation = elevation,
            shape = shape,
            backgroundColor = MaterialTheme.colors.primary,
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
                        .align(Alignment.Center),
                    wallpaperId = wallpaper.id
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