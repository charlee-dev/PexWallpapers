package com.adwi.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues

@Composable
fun PexAnimatedHeart(
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
    size: Dp = 32.dp,
) {
    val liked = updateTransition(targetState = isFavorite, label = "Heart")
    val colorState by liked.animateColor(label = "Color") { state ->
        if (state) Color.Red else Color.Transparent
    }
    val scaleState by liked.animateFloat(
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
                    tween(durationMillis = 300)
            }
        }
    ) { state ->
        if (state) 1f else 0f
    }
        Icon(
            imageVector = Icons.Outlined.Favorite,
            contentDescription = "Favorite icon",
            tint = colorState,
            modifier = modifier
                .size(size)
                .scale(scaleState)
        )
}

@Preview(showBackground = true, name = "AnimatedHeart - Light")
@Composable
fun AnimatedHeartPreview() {
    PexWallpapersTheme(darkTheme = false) {
        Column(modifier = Modifier.padding(paddingValues)) {
            PexAnimatedHeart(
                isFavorite = true
            )
        }
    }
}