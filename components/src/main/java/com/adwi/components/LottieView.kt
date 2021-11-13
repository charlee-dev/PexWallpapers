package com.adwi.components

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.*

@Composable
fun PexLottieAnimatedView(
    modifier: Modifier = Modifier,
    @RawRes res: Int
) {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(res)
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        progress = progress
    )
}