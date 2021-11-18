package com.adwi.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adwi.composables.R

@Composable
fun PexAnimatedHeart(
    modifier: Modifier = Modifier,
    state: Boolean = false,
    size: Dp = 64.dp,
    speed: Float = 1.5f
) {
    AnimatedVisibility(
        visible = state,
        modifier = modifier,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        PexLottieAnimatedView(
            res = R.raw.heart,
            modifier = Modifier.size(size),
            restartOnPlay = false,
            speed = speed
        )
    }
}