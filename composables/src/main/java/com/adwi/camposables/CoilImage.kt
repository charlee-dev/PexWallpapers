package com.adwi.camposables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter

@ExperimentalCoilApi
@Composable
fun PexCoilImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    placeholder: Int? = null,
    contentDescription: String = ""
) {
    Image(
        painter = rememberImagePainter(
            data = imageUrl,
            onExecute = { _, _ -> true },
            builder = {
                crossfade(true)
                placeholder?.let { placeholder(placeholder) }
            }
        ),
        contentDescription = contentDescription,
        modifier = modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}