package com.adwi.feature_favorites.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.*
import com.adwi.components.theme.Dimensions
import com.adwi.components.theme.Dimensions.BottomBar.BottomNavHeight
import com.adwi.components.theme.paddingValues
import com.adwi.feature_favorites.R
import com.adwi.pexwallpapers.domain.model.Wallpaper
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
    val lowRes = viewModel.lowRes

    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState()

    PexScaffold(viewModel = viewModel, scaffoldState = scaffoldState) {
        Box(modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(
                visible = wallpapers.isEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = stringResource(R.string.no_favorites_yet),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Header(
                    title = stringResource(id = R.string.favorites),
                    onActionClick = onSearchClick,
                    icon = Icons.Outlined.Favorite,
                )
                wallpapers.forEachIndexed { index, wallpaper ->
                    Spacer(modifier = Modifier.size(if (index == 0) paddingValues / 2 else paddingValues))
                    WallpaperItemVertical(
                        wallpaper = wallpaper,
                        verticalScrollState = scrollState.value - (index * 800),
                        onWallpaperClick = { onWallpaperClick(wallpaper.id) },
                        onLongPress = { viewModel.onFavoriteClick(it) },
                        isHeartEnabled = wallpaper.isFavorite,
                        modifier = Modifier.padding(horizontal = paddingValues),
                        lowRes = lowRes
                    )
                }
                Spacer(modifier = Modifier.size(BottomNavHeight + paddingValues))
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
private fun WallpaperItemVertical(
    modifier: Modifier = Modifier,
    verticalScrollState: Int,
    elevation: Dp = Dimensions.small,
    shape: Shape = MaterialTheme.shapes.small,
    wallpaper: Wallpaper,
    onWallpaperClick: (Int) -> Unit = {},
    onLongPress: (Wallpaper) -> Unit,
    isHeartEnabled: Boolean,
    lowRes: Boolean
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Card(
        elevation = elevation,
        shape = shape,
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .neumorphicShadow(isPressed = isPressed)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onWallpaperClick(wallpaper.id) },
                    onLongPress = { onLongPress(wallpaper) },
                )
            }
    ) {
        Box {
            PexCoilImage(
                imageUrl = if (lowRes) wallpaper.imageUrlTiny else wallpaper.imageUrlLandscape,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        val scale = 1.6f
                        scaleY = scale
                        scaleX = scale
                        translationY = (-verticalScrollState) * 0.1f
                    }
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