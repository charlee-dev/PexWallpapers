package com.adwi.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.domain.ColorsState
import com.adwi.components.theme.Dimensions
import com.adwi.components.theme.paddingValues
import com.adwi.core.domain.LoadingState
import com.adwi.domain.ColorCategory
import com.valentinilk.shimmer.shimmer

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun CategoryListPanel(
    modifier: Modifier = Modifier,
    state: ColorsState = ColorsState(),
    categoryName: String,
    onCategoryClick: (String) -> Unit
) {
    Column(modifier = modifier.animateContentSize()) {
        CategoryTitle(
            name = categoryName,
            modifier = Modifier.padding(horizontal = paddingValues)
        )
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
            items(items = state.categories, itemContent = { category ->
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
        if (state.loadingState is LoadingState.Loading) {
            ShimmerRow()
        }
    }
}

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
            modifier = modifier
                .size(100.dp)
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