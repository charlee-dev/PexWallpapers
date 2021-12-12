package com.adwi.components

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adwi.components.theme.Dimensions
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues

enum class Side { Left, Right, Full }

@ExperimentalMaterialApi
@Composable
fun HalfButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    halfSide: Side,
    cornerRadius: Dp,
    showShadows: Boolean = true,
    backgroundColor: Color = MaterialTheme.colors.primaryVariant,
    textColor: Color = MaterialTheme.colors.primary,
    isFullSize: Boolean,
    showText: Boolean = true,
    transitionDuration: Int = 400,
    translationY: Float = 1f
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressed = updateTransition(targetState = isPressed, label = "Pressed")

    val scaleState by pressed.animateFloat(label = "Scale") { state ->
        if (state) 0.95f else 1f
    }

    val sizeTransition = updateTransition(targetState = isFullSize, label = "Size transition")
    val cornersState by sizeTransition.animateDp(
        label = "Corners",
        transitionSpec = { tween(transitionDuration) }
    ) { state ->
        if (state) cornerRadius else 0.dp
    }

    val textTransition = updateTransition(targetState = showText, label = "Text")
    val alphaState by textTransition.animateFloat(
        label = "Alpha",
        transitionSpec = { tween(transitionDuration) }
    ) { state ->
        if (state) 1f else 0f
    }
    val translationYState by textTransition.animateFloat(
        label = "Alpha",
        transitionSpec = { tween(transitionDuration) }
    ) { state ->
        if (state) translationY else 1f
    }

    val shape = when (halfSide) {
        Side.Left -> RoundedCornerShape(
            topStart = cornerRadius,
            bottomStart = cornerRadius,
            topEnd = cornersState,
            bottomEnd = cornersState
        )
        Side.Right -> RoundedCornerShape(
            topStart = cornersState,
            bottomStart = cornersState,
            topEnd = cornerRadius,
            bottomEnd = cornerRadius
        )
        Side.Full -> RoundedCornerShape(cornerRadius)
    }

    Surface(
        onClick = onClick,
        shape = shape,
        color = backgroundColor,
        indication = null,
        interactionSource = interactionSource,
        modifier = modifier
            .neumorphicShadow(enabled = showShadows)
            .graphicsLayer(
                scaleY = scaleState,
                scaleX = scaleState
            )
            .defaultMinSize(minHeight = Dimensions.Button),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = text,
                color = textColor,
                modifier = Modifier
                    .padding(paddingValues / 2)
                    .align(Alignment.Center)
                    .graphicsLayer(
                        alpha = alphaState,
                        translationY = translationYState
                    )
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true, name = "HalfButton - Light")
@Composable
fun HalfButtonPreviewLight() {
    PexWallpapersTheme(darkTheme = false) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            ButtonPreview()
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true, name = "HalfButton - Dark")
@Composable
fun HalfButtonPreviewDark() {
    PexWallpapersTheme(darkTheme = true) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            ButtonPreview()
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun ButtonPreview() {
    HalfButton(
        onClick = {},
        text = stringResource(id = R.string.lock_screen),
        halfSide = Side.Right,
        cornerRadius = 30.dp,
        isFullSize = true,
        modifier = Modifier.height(Dimensions.Button)
    )
    Spacer(modifier = Modifier.size(paddingValues))
    HalfButton(
        onClick = {},
        text = stringResource(id = R.string.home_screen),
        halfSide = Side.Left,
        cornerRadius = 30.dp,
        isFullSize = false,
        modifier = Modifier.height(Dimensions.Button)
    )
    Spacer(modifier = Modifier.size(paddingValues))
    HalfButton(
        onClick = {},
        text = stringResource(id = R.string.set_wallpaper),
        halfSide = Side.Full,
        cornerRadius = 30.dp,
        isFullSize = true,
        modifier = Modifier.height(Dimensions.Button)
    )
}