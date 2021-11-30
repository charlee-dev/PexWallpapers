package com.adwi.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adwi.components.theme.*

/*
* to capture pressed state add:
*
* val interactionSource = remember { MutableInteractionSource() }
*   val isPressed by interactionSource.collectIsPressedAsState()
*
* add pass pressed to modifier's constructor
*
* */

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.neumorphicShadow(
    pressed: Boolean = false,
    lightLightShadow: Color = PrimaryShadowLight,
    lightDarkShadow: Color = PrimaryShadowDark,
    alpha: Float = .9f,
    cornerRadius: Dp = 30.dp,
    shadowRadius: Dp = 10.dp,
    offset: Dp = (-10).dp,
) = composed {

    // Check if dark or light system theme
    val isLightTheme = MaterialTheme.colors.isLight

    val colorTop = if (isLightTheme) lightLightShadow else Neutral3
    val colorBottom = if (isLightTheme) lightDarkShadow else Neutral5
    val darkAlpha = if (isLightTheme) alpha else 0.1f

    // Animate shadow if view is clickable
    val transition = updateTransition(targetState = pressed, label = "Shadow")
    val elevation by transition.animateDp(label = "Card elevation") { state ->
        if (state) 0.dp else offset
    }

    this.drawBehind {

        // Getting colors
        val transparentColor =
            android.graphics.Color.toArgb(colorTop.copy(alpha = 0.0f).value.toLong())
        val shadowColorTop =
            android.graphics.Color.toArgb(colorTop.copy(alpha = darkAlpha).value.toLong())
        val shadowColorBottom =
            android.graphics.Color.toArgb(colorBottom.copy(alpha = alpha).value.toLong())

        // Top left light shadow
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            frameworkPaint.color = transparentColor
            frameworkPaint.setShadowLayer(
                shadowRadius.toPx(),
                elevation.toPx(),
                elevation.toPx(),
                shadowColorTop
            )
            it.drawRoundRect(
                0f,
                0f,
                this.size.width,
                this.size.height,
                cornerRadius.toPx(),
                cornerRadius.toPx(),
                paint
            )
        }

        // Bottom right shadow
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            frameworkPaint.color = transparentColor
            frameworkPaint.setShadowLayer(
                shadowRadius.toPx(),
                (-offset).toPx(),
                (-offset).toPx(),
                shadowColorBottom
            )
            it.drawRoundRect(
                0f,
                0f,
                this.size.width,
                this.size.height,
                cornerRadius.toPx(),
                cornerRadius.toPx(),
                paint
            )
        }
    }
}

@Preview(showBackground = true, name = "Light")
@Composable
private fun NeumorphicPreviewLight() {
    PexWallpapersTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            Card(
                shape = MaterialTheme.shapes.large,
                backgroundColor = MaterialTheme.colors.surface,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .neumorphicShadow(),
                content = {}
            )
        }
    }
}

@Preview(showBackground = true, name = "Dark")
@Composable
private fun NeumorphicPreviewDark() {
    PexWallpapersTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            Card(
                shape = MaterialTheme.shapes.large,
                backgroundColor = MaterialTheme.colors.surface,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .neumorphicShadow(),
                content = {}
            )
        }
    }
}