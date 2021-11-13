package com.adwi.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.adwi.composables.R
import com.adwi.domain.Wallpaper

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun PreviewCard(
    modifier: Modifier = Modifier,
    placeholder: Int = R.drawable.image_placeholder,
    imageUrl: String,
    elevation: Dp = 10.dp,
    shape: Shape = MaterialTheme.shapes.large,
) {
    Card(
        elevation = elevation,
        shape = shape,
        modifier = modifier
            .fillMaxSize()
    ) {
        PexCoilImage(
            imageUrl = imageUrl,
            placeholder = placeholder
        )
    }
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Preview(showBackground = true)
@Composable
fun PreviewCardPreview() {
    MaterialTheme {
        PreviewCard(imageUrl = Wallpaper.defaultDaily.imageUrlPortrait)
    }
}