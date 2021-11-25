package com.adwi.pexwallpapers.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.adwi.pexwallpapers.ui.theme.paddingValues

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun PreviewCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    elevation: Dp = 10.dp,
    shape: Shape = MaterialTheme.shapes.large,
    wallpaperId: Int,
    onLongPress: () -> Unit,
    isHeartEnabled: Boolean
) {
    Card(
        elevation = elevation,
        shape = shape,
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { onLongPress() }
                )
            }
    ) {
        Box {
            PexCoilImage(
                imageUrl = imageUrl,
                wallpaperId = wallpaperId,
                modifier = Modifier.fillMaxSize()
            )
            PexAnimatedHeart(
                state = isHeartEnabled,
                size = 128.dp,
                speed = 1.5f,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(paddingValues)
            )
        }
    }
}