package com.adwi.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues

@Composable
fun CategoryTitle(
    modifier: Modifier = Modifier,
    name: String
) {
    Text(
        text = name,
        style = MaterialTheme.typography.h3
            .merge(TextStyle(color = MaterialTheme.colors.onBackground)),
        modifier = modifier
    )
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
            CategoryTitle(name = "Colors")
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
            CategoryTitle(name = "Colors")
        }
    }
}