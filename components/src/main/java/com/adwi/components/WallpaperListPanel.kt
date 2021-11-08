package com.adwi.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.adwi.camposables.PexCoilImage
import com.adwi.components.theme.Dimensions
import com.adwi.components.theme.paddingValues
import com.adwi.core.domain.ProgressBarState
import com.adwi.core.domain.WallpaperListState
import com.valentinilk.shimmer.shimmer

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

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun WallpaperListPanel(
    modifier: Modifier = Modifier,
    state: WallpaperListState = WallpaperListState(),
    categoryName: String = "",
    onWallpaperClick: (Long) -> Unit = {}
) {
    Column(modifier = modifier) {
        CategoryTitle(
            name = categoryName,
            modifier = Modifier.padding(horizontal = paddingValues)
        )
        if (state.progressBarState is ProgressBarState.Loading) {
            ShimmerRow()
        }
        LazyRow(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                start = paddingValues,
                end = paddingValues
            ),
            horizontalArrangement = Arrangement.spacedBy(paddingValues)
        ) {
            items(items = state.wallpapers, itemContent = { wallpaper ->
                WallpaperItem(
                    image = wallpaper.imageUrlTiny,
                    onWallpaperClick = { onWallpaperClick(wallpaper.id) }
                )
            })
        }
    }
}

@Composable
fun CategoryTitle(
    modifier: Modifier = Modifier,
    name: String
) {
    Text(
        text = name,
        style = MaterialTheme.typography.h3
            .merge(TextStyle(color = MaterialTheme.colors.onBackground)),
        modifier = modifier.padding(vertical = paddingValues / 2)
    )
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
private fun WallpaperItem(
    modifier: Modifier = Modifier,
    elevation: Dp = Dimensions.small,
    shape: Shape = MaterialTheme.shapes.small,
    image: String = "",
    onWallpaperClick: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            onClick = onWallpaperClick,
            elevation = elevation,
            shape = shape,
            modifier = modifier
                .size(100.dp)
        ) {
            PexCoilImage(
                imageUrl = image,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@ExperimentalMaterialApi

@Preview(showBackground = true)
@Composable
fun CategoryTitlePreview() {
    MaterialTheme {
        CategoryTitle(
            name = "Colors"
        )
    }
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun CategoryItemPreview() {
    MaterialTheme {
        WallpaperItem()
    }
}