package com.adwi.favorites

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.Header
import com.adwi.components.PexAnimatedHeart
import com.adwi.components.PexCoilImage
import com.adwi.components.PexScaffold
import com.adwi.pexwallpapers.components.theme.Dimensions
import com.adwi.pexwallpapers.components.theme.Dimensions.BottomBar.BottomNavHeight
import com.adwi.pexwallpapers.components.theme.paddingValues
import com.adwi.domain.Wallpaper
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    onSearchClick: () -> Unit,
    onWallpaperClick: (Int) -> Unit,
) {
    val wallpapers by viewModel.wallpapers.collectAsState()

    val scaffoldState = rememberScaffoldState()

    PexScaffold(viewModel = viewModel, scaffoldState = scaffoldState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = paddingValues)
        ) {
            Header(
                title = stringResource(id = R.string.favorites),
                onActionClick = onSearchClick
            )
            AnimatedVisibility(
                visible = wallpapers.isNullOrEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "No favorites yet",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    bottom = BottomNavHeight + paddingValues
                ),
                verticalArrangement = Arrangement.spacedBy(paddingValues / 2)
            ) {
                items(items = wallpapers, itemContent = { wallpaper ->

                    WallpaperItemVertical(
                        wallpaper = wallpaper,
                        onWallpaperClick = { onWallpaperClick(wallpaper.id) },
                        onLongPress = { viewModel.onFavoriteClick(it) },
                        isHeartEnabled = wallpaper.isFavorite
                    )
                })

            }
        }
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