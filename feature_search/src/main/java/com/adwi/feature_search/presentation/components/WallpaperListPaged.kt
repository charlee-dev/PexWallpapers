package com.adwi.feature_search.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.PexAnimatedHeart
import com.adwi.components.PexCoilImage
import com.adwi.components.neumorphicShadow
import com.adwi.components.theme.paddingValues
import com.adwi.data.database.domain.WallpaperEntity
import com.adwi.data.database.domain.toDomain
import com.adwi.pexwallpapers.domain.model.Wallpaper

@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun WallpaperListPaged(
    modifier: Modifier = Modifier,
    wallpapers: LazyPagingItems<WallpaperEntity>,
    onWallpaperClick: (Int) -> Unit,
    onLongPress: (Wallpaper) -> Unit,
    listState: LazyListState,
    lowRes: Boolean,
    showShadows: Boolean
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    LazyColumn(
        modifier = modifier,
        state = listState,
        contentPadding = PaddingValues(
            top = paddingValues,
            bottom = paddingValues * 3
        ),
        verticalArrangement = Arrangement.spacedBy(paddingValues / 2)
    ) {
        items(wallpapers.itemCount) { index ->
            wallpapers[index]?.let {
                val wallpaper = it.toDomain()
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = paddingValues)
                        .height((wallpaper.height / 2.5).dp)
                        .neumorphicShadow(enabled = showShadows, isPressed = isPressed)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = { onWallpaperClick(wallpaper.id) },
                                onLongPress = {
                                    onLongPress(wallpaper)
                                }
                            )
                        },
//                    elevation = 20.dp,
                    shape = MaterialTheme.shapes.large
                ) {
                    Box {
                        PexCoilImage(
                            imageUrl = if (lowRes) wallpaper.imageUrlTiny else wallpaper.imageUrlPortrait,
                            modifier = Modifier.fillMaxSize()
                        )
                        PexAnimatedHeart(
                            state = wallpaper.isFavorite,
                            size = 64.dp,
                            speed = 1.5f,
                            modifier = Modifier.align(Alignment.TopEnd)
                        )
                    }
                }
            }
        }
    }
}