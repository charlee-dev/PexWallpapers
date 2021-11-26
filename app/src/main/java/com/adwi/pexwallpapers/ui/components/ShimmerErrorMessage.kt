package com.adwi.pexwallpapers.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adwi.pexwallpapers.ui.theme.paddingValues
import com.valentinilk.shimmer.shimmer

@ExperimentalMaterialApi
@Composable
fun ShimmerErrorMessage(
    modifier: Modifier = Modifier,
    visible: Boolean,
    message: String = "",
    elevation: Dp = 10.dp,
    shape: Shape = MaterialTheme.shapes.large,
    backgroundColor: Color = MaterialTheme.colors.primary,
    textColor: Color = MaterialTheme.colors.onPrimary,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(Modifier.fillMaxWidth()) {
            Card(
                elevation = elevation,
                shape = shape,
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .shimmer()
                    .padding(bottom = paddingValues)
                    .neumorphicPunched()
            ) {
                Box(modifier = Modifier.background(backgroundColor)) {
                    LoadingErrorText(
                        visible = true,
                        message = message,
                        textColor = textColor,
                        modifier = Modifier
                            .padding(paddingValues / 2)
                            .align(Alignment.Center)
                    )
                }
            }

        }
    }
}