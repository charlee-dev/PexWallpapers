package com.adwi.feature_preview.presentation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adwi.components.ActionButton
import com.adwi.components.HalfButton
import com.adwi.components.R
import com.adwi.components.Side

const val TRANSITION_DURATION = 300
private const val TRANSLATION_Y = 1.2f

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun SetWallpaperButton(
    modifier: Modifier = Modifier,
    showShadows: Boolean = true,
    onHomeClick: () -> Unit,
    onLockClick: () -> Unit,
    cornerRadius: Dp = 30.dp,
    backgroundColor: Color = MaterialTheme.colors.primaryVariant,
    textColor: Color = MaterialTheme.colors.primary,
    enabled: Boolean = true
) {
    var isSplit by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = isSplit, label = "Split")

    val backgroundColorState by transition.animateColor(
        label = "Color background",
        transitionSpec = { tween(TRANSITION_DURATION) }
    ) { state ->
        if (state) MaterialTheme.colors.primary else backgroundColor
    }
    val textColorState by transition.animateColor(
        label = "Color text",
        transitionSpec = { tween(TRANSITION_DURATION) }
    ) { state ->
        if (state) MaterialTheme.colors.secondary else textColor
    }
    val scaleState by transition.animateFloat(
        label = "Scale",
        transitionSpec = {
            when {
                false isTransitioningTo true ->
                    spring(
                        stiffness = Spring.StiffnessLow,
                        dampingRatio = Spring.DampingRatioHighBouncy,
                        visibilityThreshold = .5f
                    )
                else ->
                    tween(durationMillis = TRANSITION_DURATION)
            }
        }
    ) { state ->
        if (state) 1f else 0f
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        HalfButton( // This view is used only to cast full shadow under buttons
            onClick = {},
            text = "",
            halfSide = Side.Full,
            cornerRadius = cornerRadius,
            isFullSize = isSplit,
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color.Transparent,
            showShadows = !isSplit && showShadows,
            transitionDuration = TRANSITION_DURATION
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            HalfButton(
                onClick = { if (enabled) onHomeClick() },
                text = stringResource(id = R.string.home_screen),
                halfSide = Side.Left,
                cornerRadius = cornerRadius,
                isFullSize = isSplit,
                showShadows = isSplit && showShadows,
                showText = isSplit,
                transitionDuration = TRANSITION_DURATION,
                translationY = TRANSLATION_Y,
                modifier = Modifier
                    .weight(1f)
            )
            if (isSplit) {
                ActionButton(
                    onClick = { isSplit = false },
                    icon = Icons.Outlined.Close,
                    contentDescription = "Close",
                    modifier = Modifier
                        .scale(scaleState)
                )
            }
            HalfButton(
                onClick = { if (enabled) onLockClick() },
                text = stringResource(id = R.string.lock_screen),
                halfSide = Side.Right,
                cornerRadius = cornerRadius,
                isFullSize = isSplit,
                showShadows = isSplit && showShadows,
                showText = isSplit,
                backgroundColor = backgroundColorState,
                textColor = textColorState,
                translationY = -TRANSLATION_Y,
                transitionDuration = TRANSITION_DURATION,
                modifier = Modifier
                    .weight(1f)
            )
        }
        AnimatedVisibility(
            visible = !isSplit,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            HalfButton(
                onClick = { isSplit = true },
                text = stringResource(id = R.string.set_wallpaper),
                halfSide = Side.Full,
                cornerRadius = cornerRadius,
                isFullSize = isSplit,
                showText = !isSplit,
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color.Transparent,
                showShadows = false
            )
        }
    }
}