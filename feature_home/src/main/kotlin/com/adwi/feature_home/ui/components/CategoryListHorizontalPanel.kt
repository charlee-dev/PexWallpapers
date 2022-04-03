package com.adwi.feature_home.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.CategoryTitle
import com.adwi.components.PexCoilImage
import com.adwi.components.neumorphicShadow
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues
import com.adwi.pexwallpapers.domain.model.Wallpaper

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun CategoryListHorizontalPanel(
    modifier: Modifier = Modifier,
    colors: List<Wallpaper>,
    listState: LazyListState = rememberLazyListState(),
    onCategoryClick: (String) -> Unit,
    showShadows: Boolean
) {
    Column(modifier = modifier.animateContentSize()) {
        Box {
            ShimmerRow(
                visible = colors.isEmpty(),
                showShadows = showShadows
            )
            ShimmerErrorMessage(
                visible = colors.isEmpty(),
                message = "Something bad just happened.. :/",
                modifier = Modifier.padding(horizontal = paddingValues),
                showShadows = showShadows
            )
            CategoryListHorizontal(
                categoryList = colors,
                onCategoryClick = onCategoryClick,
                listState = listState,
                showShadows = showShadows
            )
        }
    }
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun CategoryListHorizontal(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    categoryList: List<Wallpaper>,
    onCategoryClick: (String) -> Unit,
    showShadows: Boolean = true
) {
    LazyRow(
        state = listState,
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(
            start = paddingValues,
            end = paddingValues
        ),
        horizontalArrangement = Arrangement.spacedBy(paddingValues)
    ) {
        items(items = categoryList, itemContent = { category ->
            CategoryItem(
                categoryName = category.categoryName,
                image = category.imageUrlTiny,
                onCategoryClick = { onCategoryClick(category.categoryName) },
                modifier = Modifier.padding(top = paddingValues / 2),
                showShadows = showShadows
            )
        })
    }
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
private fun CategoryItem(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    categoryName: String,
    image: String,
    onCategoryClick: () -> Unit,
    showShadows: Boolean = true
) {
    var isPressed by remember { mutableStateOf(false) }
    val pressed = updateTransition(targetState = isPressed, label = "Press")
    val scale by pressed.animateFloat(label = "Scale") {
        if (it) .98f else 1f
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = shape,
            backgroundColor = MaterialTheme.colors.primary,
            modifier = modifier
                .size(100.dp)
                .neumorphicShadow(
                    enabled = showShadows,
                    cornerRadius = 10.dp,
                    offset = (-7).dp
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { onCategoryClick() },
                        onPress = {
                            isPressed = true
                            this.tryAwaitRelease()
                            isPressed = false
                        }
                    )
                }
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
        ) {
            PexCoilImage(
                imageUrl = image,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.size(paddingValues / 4))
        Text(
            modifier = Modifier
                .width(width = 100.dp)
                .align(Alignment.CenterHorizontally)
                .horizontalScroll(
                    state = rememberScrollState()
                ),
            textAlign = TextAlign.Center,
            text = categoryName,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle1
                .merge(TextStyle(color = MaterialTheme.colors.onBackground))
        )
    }
}

@Preview(showBackground = true, name = "Light")
@Composable
private fun CategoryTitlePreviewLight() {
    PexWallpapersTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            CategoryTitle(
                name = "Colors"
            )
        }
    }
}

@Preview(showBackground = true, name = "Dark")
@Composable
private fun CategoryTitlePreviewDark() {
    PexWallpapersTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            CategoryTitle(
                name = "Colors"
            )
        }
    }
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Preview(showBackground = true, name = "Light")
@Composable
private fun CategoryItemPreviewLight() {
    PexWallpapersTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            CategoryItem(
                categoryName = Wallpaper.defaultDaily.categoryName,
                image = Wallpaper.defaultDaily.imageUrlTiny,
                onCategoryClick = {}
            )
        }
    }
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Preview(showBackground = true, name = "Dark")
@Composable
private fun CategoryItemPreviewDark() {
    PexWallpapersTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            CategoryItem(
                categoryName = Wallpaper.defaultDaily.categoryName,
                image = Wallpaper.defaultDaily.imageUrlTiny,
                onCategoryClick = {}
            )
        }
    }
}