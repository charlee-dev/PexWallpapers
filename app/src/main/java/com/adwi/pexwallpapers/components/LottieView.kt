package com.adwi.pexwallpapers.components

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun PexLottieAnimatedView(
    modifier: Modifier = Modifier,
    @RawRes res: Int,
    restartOnPlay: Boolean = true,
    speed: Float = 1f
) {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(res)
    )

//    val progress by animateLottieCompositionAsState(
//        composition = composition,
//        iterations = LottieConstants.IterateForever,
//    )
    LottieAnimation(
        modifier = modifier.fillMaxSize(),
        composition = composition,
        restartOnPlay = restartOnPlay,
        speed = speed
    )
}