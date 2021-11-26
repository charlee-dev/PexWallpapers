package com.adwi.pexwallpapers.ui.components

import androidx.compose.animation.animateContentSize
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.adwi.pexwallpapers.R
import com.adwi.pexwallpapers.model.ColorCategory
import com.adwi.pexwallpapers.ui.theme.Dimensions
import com.adwi.pexwallpapers.ui.theme.PexWallpapersTheme
import com.adwi.pexwallpapers.ui.theme.paddingValues
import com.adwi.pexwallpapers.util.domain.DataState

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun CategoryListHorizontalPanel(
    modifier: Modifier = Modifier,
    colors: DataState<List<ColorCategory>>,
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
                message = stringResource(
                    id = R.string.could_not_refresh,
                    colors.error?.localizedMessage
                        ?: stringResource(R.string.unknown_error_occurred)
                ),
                modifier = Modifier.padding(horizontal = paddingValues)
            )
            colors.data?.let { list ->
                CategoryListHorizontal(
                    categoryList = list,
                    onCategoryClick = onCategoryClick,
                    listState = listState
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
                image1 = category.firstImage,
                image2 = category.secondImage,
                image3 = category.thirdImage,
                image4 = category.forthImage,
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
    elevation: Dp = Dimensions.small,
    shape: Shape = MaterialTheme.shapes.small,
    categoryName: String,
    image1: String,
    image2: String,
    image3: String,
    image4: String,
    onCategoryClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            onClick = onCategoryClick,
            elevation = elevation,
            shape = shape,
            backgroundColor = MaterialTheme.colors.primary,
            modifier = modifier.size(100.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                PexCoilImage(
                    imageUrl = image1,
                    modifier = Modifier
                        .fillMaxSize(.5f)
                        .align(Alignment.TopStart)
                )
                PexCoilImage(
                    imageUrl = image2,
                    modifier = Modifier
                        .fillMaxSize(.5f)
                        .align(Alignment.TopEnd)
                )
                PexCoilImage(
                    imageUrl = image3,
                    modifier = Modifier
                        .fillMaxSize(.5f)
                        .align(Alignment.BottomStart)
                )
                PexCoilImage(
                    imageUrl = image4,
                    modifier = Modifier
                        .fillMaxSize(.5f)
                        .align(Alignment.BottomEnd)
                )
            }
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

@Preview(showBackground = true)
@Composable
private fun CategoryTitlePreview() {
    PexWallpapersTheme {
        CategoryTitle(
            name = "Colors"
        )
    }
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
private fun CategoryItemPreview() {
    PexWallpapersTheme() {
        CategoryItem(
            categoryName = ColorCategory.mock.name,
            image1 = ColorCategory.mock.firstImage,
            image2 = ColorCategory.mock.secondImage,
            image3 = ColorCategory.mock.thirdImage,
            image4 = ColorCategory.mock.forthImage,
            onCategoryClick = {}
        )
    }
}