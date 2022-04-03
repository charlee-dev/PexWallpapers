package com.adwi.feature_home.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adwi.components.neumorphicShadow
import com.adwi.components.theme.Dimensions
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues
import com.valentinilk.shimmer.shimmer

@Composable
fun ShimmerRow(
    modifier: Modifier = Modifier,
    visible: Boolean,
    elevation: Dp = Dimensions.small,
    shape: Shape = MaterialTheme.shapes.small,
    backgroundColor: Color = MaterialTheme.colors.secondaryVariant,
    showShadows: Boolean
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(modifier = modifier.fillMaxWidth()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .shimmer(),
                contentPadding = PaddingValues(start = paddingValues),
                horizontalArrangement = Arrangement.spacedBy(paddingValues / 2)
            ) {
                items(5) {
                    Card(
                        elevation = elevation,
                        modifier = modifier
                            .padding(paddingValues / 2)
                            .size(100.dp)
                            .shimmer()
                            .neumorphicShadow(enabled = showShadows),
                        shape = shape,
                        backgroundColor = backgroundColor,
                        content = {}
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Light")
@Composable
private fun ShimmerRowPreviewLight() {
    PexWallpapersTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            ShimmerRow(
                visible = true,
                showShadows = true
            )
        }
    }
}

@Preview(showBackground = true, name = "Dark")
@Composable
private fun ShimmerRowPreviewDark() {
    PexWallpapersTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            ShimmerRow(
                visible = true,
                showShadows = true
            )
        }
    }
}