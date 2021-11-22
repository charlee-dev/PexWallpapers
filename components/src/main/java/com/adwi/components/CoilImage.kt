package com.adwi.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter

@ExperimentalCoilApi
@Composable
fun PexCoilImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    placeholder: Int? = null,
    contentDescription: String = "",
    isSquare: Boolean = false
) {
    if (isSquare) modifier.size(200.dp)
    Image(
        painter = rememberImagePainter(
            data = imageUrl,
//            onExecute = { _, _ -> true },
            builder = {
                crossfade(true)
                placeholder?.let { placeholder(placeholder) }
            }
        ),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier,
    )
}