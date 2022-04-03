package com.adwi.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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

@Preview(showBackground = true, name = "Light")
@Composable
fun CategoryTitlePreviewLight() {
    PexWallpapersTheme(darkTheme = false) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(horizontal = paddingValues),
            verticalArrangement = Arrangement.spacedBy(paddingValues)
        ) {
            CategoryTitle(name = "Colors")
            CategoryTitle(name = "Colors")
            CategoryTitle(name = "Colors")
            CategoryTitle(name = "Colors")
            CategoryTitle(name = "Colors")

        }
    }
}