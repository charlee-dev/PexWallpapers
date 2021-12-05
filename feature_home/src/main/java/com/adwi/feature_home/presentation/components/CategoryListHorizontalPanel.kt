package com.adwi.feature_home.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.CategoryPanel
import com.adwi.components.CategoryTitle
import com.adwi.components.PexCoilImage
import com.adwi.components.neumorphicShadow
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues
import com.adwi.pexwallpapers.domain.model.ColorCategory

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun CategoryListHorizontalPanel(
    modifier: Modifier = Modifier,
    verticalScrollState: ScrollState,
    colors: com.adwi.core.DataState<List<ColorCategory>>,
    listState: LazyListState = rememberLazyListState(),
    panelTitle: String,
    onCategoryClick: (String) -> Unit
) {
    Column(modifier = modifier.animateContentSize()) {
        CategoryPanel(
            categoryName = panelTitle,
            modifier = Modifier.padding(horizontal = paddingValues)
        )
        Box {
            ShimmerRow(
                visible = colors.data.isNullOrEmpty() && colors.error == null
            )
            ShimmerErrorMessage(
                visible = colors.data.isNullOrEmpty() && colors.error != null,
                message = colors.error?.localizedMessage ?: "Unknown error",
                modifier = Modifier.padding(horizontal = paddingValues)
            )
            colors.data?.let { list ->
                CategoryListHorizontal(
                    categoryList = list,
                    verticalScrollState = verticalScrollState,
                    onCategoryClick = onCategoryClick,
                    listState = listState,
                )
            }
        }
    }
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun CategoryListHorizontal(
    modifier: Modifier = Modifier,
    verticalScrollState: ScrollState,
    listState: LazyListState = rememberLazyListState(),
    categoryList: List<ColorCategory>,
    onCategoryClick: (String) -> Unit
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
                categoryName = category.name,
                verticalScrollState = verticalScrollState,
                image = category.firstImage,
                onCategoryClick = { onCategoryClick(category.name) }
            )
        })
    }
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
private fun CategoryItem(
    modifier: Modifier = Modifier,
    verticalScrollState: ScrollState,
    shape: Shape = MaterialTheme.shapes.small,
    categoryName: String,
    image: String,
    onCategoryClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            onClick = onCategoryClick,
            shape = shape,
            backgroundColor = MaterialTheme.colors.primary,
            modifier = modifier
                .size(100.dp)
                .neumorphicShadow(
                    isPressed = isPressed,
                    cornerRadius = 10.dp,
                    offset = (-5).dp
                ),
            interactionSource = interactionSource
        ) {
                PexCoilImage(
                    imageUrl = image,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            val scale = 1.6f
                            scaleY = scale
                            scaleX = scale
                            translationY = (-verticalScrollState.value) * 0.1f
                        }
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
                categoryName = ColorCategory.mock.name,
                verticalScrollState = rememberScrollState(),
                image = ColorCategory.mock.firstImage,
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
                categoryName = ColorCategory.mock.name,
                verticalScrollState = rememberScrollState(),
                image = ColorCategory.mock.firstImage,
                onCategoryClick = {}
            )
        }
    }
}