package com.adwi.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues

@ExperimentalMaterialApi
@Composable
fun ImageActionButtons(
    modifier: Modifier = Modifier,
    onGoToUrlClick: () -> Unit,
    onShareClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    isFavorite: Boolean
) {
    val favoriteTransition = updateTransition(targetState = isFavorite, label = "Card")

    val tintColor by favoriteTransition.animateColor(label = "Favorite icon color") { state ->
        if (state) Color.Red else MaterialTheme.colors.onBackground
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ActionButton(
            icon = Icons.Outlined.Link,
            onClick = onGoToUrlClick
        )
        ActionButton(
            icon = Icons.Outlined.Save,
            onClick = onDownloadClick
        )
        ActionButton(
            icon = Icons.Outlined.Share,
            onClick = onShareClick
        )
        ActionButton(
            icon = Icons.Outlined.Favorite,
            tint = tintColor,
            onClick = onFavoriteClick
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Outlined.Save,
    tint: Color = MaterialTheme.colors.onBackground,
    onClick: () -> Unit,
    contentDescription: String = ""
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val transition = updateTransition(targetState = isPressed, label = "Button state")

    val scaleState by transition.animateFloat(
        label = "Scale", transitionSpec = { tween(300) }
    ) { state ->
        if (state) .9f else 1.2f
    }
    val colorState by transition.animateColor(label = "Color") { state ->
        if (state) MaterialTheme.colors.secondaryVariant else tint
    }

    Surface(
        onClick = onClick,
        interactionSource = interactionSource,
        indication = null,
        color = Color.Transparent,
        modifier = Modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = colorState,
            modifier = modifier
                .padding(horizontal = paddingValues / 2)
                .graphicsLayer(
                    scaleX = scaleState,
                    scaleY = scaleState
                )
        )
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true, name = "Light")
@Composable
private fun ImageActionButtonsPreviewLight() {
    PexWallpapersTheme(darkTheme = false) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            ImageActionButtons(
                onGoToUrlClick = {},
                onShareClick = {},
                onDownloadClick = {},
                onFavoriteClick = {},
                isFavorite = true
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true, name = "Dark")
@Composable
private fun ImageActionButtonsPreviewDark() {
    PexWallpapersTheme(darkTheme = true) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            ImageActionButtons(
                onGoToUrlClick = {},
                onShareClick = {},
                onDownloadClick = {},
                onFavoriteClick = {},
                isFavorite = true
            )
        }
    }
}