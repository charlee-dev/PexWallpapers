package com.adwi.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloseFullscreen
import androidx.compose.material.icons.outlined.ZoomIn
import androidx.compose.material.icons.outlined.ZoomOut
import androidx.compose.material.icons.outlined.ZoomOutMap
import androidx.compose.material.icons.rounded.OpenInFull
import androidx.compose.material.icons.rounded.ZoomIn
import androidx.compose.material.icons.rounded.ZoomOut
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues
import com.adwi.pexwallpapers.domain.model.Wallpaper

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun PreviewCard(
    modifier: Modifier = Modifier,
    wallpaper: Wallpaper,
    shape: Shape = MaterialTheme.shapes.large,
    onLongPress: () -> Unit,
    showShadows: Boolean,
    onWallpaperClick: () -> Unit,
    inPreview: Boolean
) {
    var isPressed by remember { mutableStateOf(false) }
    val pressed = updateTransition(targetState = isPressed, label = "Press")

    val scale by pressed.animateFloat(
        label = "Scale",
        transitionSpec = { tween(400) }
    ) {
        if (it) .99f else 1f
    }

    Card(
        shape = shape,
        modifier = modifier
            .fillMaxSize()
            .neumorphicShadow(enabled = showShadows)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onWallpaperClick() },
                    onLongPress = { onLongPress() },
                    onPress = {
                        isPressed = true
                        this.tryAwaitRelease()
                        isPressed = false
                    }
                )
            }
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
    ) {
        Box(modifier = Modifier) {
            PexCoilImage(
                imageUrl = wallpaper.imageUrlPortrait,
                wallpaperId = wallpaper.id,
                modifier = Modifier.fillMaxSize()
            )
            PexAnimatedHeart(
                isFavorite = wallpaper.isFavorite,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(paddingValues)
            )
            Icon(
                imageVector = if (inPreview) Icons.Outlined.CloseFullscreen else Icons.Rounded.OpenInFull,
                contentDescription = stringResource(R.string.show_bigger),
                tint = MaterialTheme.colors.background,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(paddingValues)
                    .neumorphicShadow(offset = 0.dp, alpha = .1f)
            )
        }
    }
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Preview(showBackground = true, name = "Light")
@Composable
private fun PreviewCardPreviewLight() {
    PexWallpapersTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            PreviewCard(
                wallpaper = Wallpaper.defaultDaily,
                showShadows = true,
                onLongPress = {},
                onWallpaperClick = {},
                inPreview = true
            )
        }
    }
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Preview(showBackground = true, name = "Dark")
@Composable
private fun PreviewCardPreviewDark() {
    PexWallpapersTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            PreviewCard(
                wallpaper = Wallpaper.defaultDaily,
                showShadows = true,
                onLongPress = {},
                onWallpaperClick = {},
                inPreview = true
            )
        }
    }
}