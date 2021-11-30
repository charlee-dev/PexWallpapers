package com.adwi.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues

@ExperimentalMaterialApi
@Composable
fun CategoryPanel(
    modifier: Modifier = Modifier,
    categoryName: String,
    onShowMoreClick: () -> Unit = {},
    showMore: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = modifier
    ) {
        CategoryTitle(
            name = categoryName,
            modifier = Modifier
                .weight(1f)
        )
        if (showMore) ShowMore(onShowMoreClick = onShowMoreClick)
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
@Composable
fun ShowMore(
    modifier: Modifier = Modifier,
    onShowMoreClick: () -> Unit,
) {
    Surface(
        onClick = onShowMoreClick,
        color = MaterialTheme.colors.background,
        modifier = modifier.padding(vertical = paddingValues / 2)
    ) {
        Text(text = "Show more")
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true, name = "CategoryHeader Light")
@Composable
private fun CategoryHeaderPreviewLight() {
    PexWallpapersTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            CategoryPanel(categoryName = "Colors")
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true, name = "CategoryHeader Dark")
@Composable
private fun CategoryHeaderPreviewDark() {
    PexWallpapersTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            CategoryPanel(categoryName = "Colors")
        }
    }
}