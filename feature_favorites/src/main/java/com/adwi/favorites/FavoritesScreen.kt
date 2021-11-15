package com.adwi.favorites

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.Header
import com.adwi.components.PexAnimatedHeart
import com.adwi.components.PexCoilImage
import com.adwi.components.theme.Dimensions
import com.adwi.components.theme.Dimensions.BottomBar.BottomNavHeight
import com.adwi.components.theme.paddingValues
import com.adwi.core.domain.LoadingState
import com.adwi.domain.Wallpaper
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun FavoritesScreen(
    state: FavoritesState,
    onTriggerEvent: (FavoritesEvent) -> Unit,
    onSearchClick: () -> Unit,
    onWallpaperClick: (Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = paddingValues,
            end = paddingValues,
            bottom = BottomNavHeight + paddingValues
        ),
        verticalArrangement = Arrangement.spacedBy(paddingValues / 2)
    ) {
        item {
            Header(
                title = stringResource(id = R.string.favorites),
                onSearchClick = onSearchClick
            )
        }
        items(items = state.favorites.value.wallpapers, itemContent = { wallpaper ->
            var isHeartEnabled by remember { mutableStateOf(wallpaper.isFavorite) }

            WallpaperItemVertical(
                wallpaper = wallpaper,
                onWallpaperClick = { onWallpaperClick(wallpaper.id) },
                onLongPress = { item ->
                    isHeartEnabled = !isHeartEnabled
                    onTriggerEvent(FavoritesEvent.OnFavoriteClick(item))
                },
                isHeartEnabled = isHeartEnabled
            )
        })
    }
    if (state.loadingState is LoadingState.Loading) {
        Text(text = "No favorites yet")
    }
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
private fun WallpaperItemVertical(
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
            .fillMaxWidth()
            .height(200.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onWallpaperClick(wallpaper.id) },
                    onLongPress = { onLongPress(wallpaper) },
                )
            }
    ) {
        Box {
            PexCoilImage(
                imageUrl = wallpaper.imageUrlLandscape,
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