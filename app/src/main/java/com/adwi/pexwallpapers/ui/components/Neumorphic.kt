package com.adwi.pexwallpapers.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adwi.pexwallpapers.ui.theme.Neutral3
import com.adwi.pexwallpapers.ui.theme.Neutral5
import com.adwi.pexwallpapers.ui.theme.PrimaryShadowDark
import com.adwi.pexwallpapers.ui.theme.PrimaryShadowLight

/*
* to capture pressed state add:
*
* val interactionSource = remember { MutableInteractionSource() }
*   val isPressed by interactionSource.collectIsPressedAsState()
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

    val isLightTheme = MaterialTheme.colors.isLight

    val colorTop = if (isLightTheme) lightLightShadow else Neutral3
    val colorBottom = if (isLightTheme) lightDarkShadow else Neutral5
    val darkAlpha = if (isLightTheme) alpha else 0.1f

    val transition = updateTransition(targetState = pressed, label = "Shadow")

    val elevation by transition.animateDp(label = "Card elevation") { state ->
        if (state) 0.dp else offset
    }

    this.drawBehind {
        val transparentColor =
            android.graphics.Color.toArgb(colorTop.copy(alpha = 0.0f).value.toLong())
        val shadowColorTop =
            android.graphics.Color.toArgb(colorTop.copy(alpha = darkAlpha).value.toLong())
        val shadowColorBottom =
            android.graphics.Color.toArgb(colorBottom.copy(alpha = alpha).value.toLong())

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

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.coloredShadowBottom(
    color: Color = PrimaryShadowDark,
    alpha: Float = .9f,
    borderRadius: Dp = 0.dp,
    shadowRadius: Dp = 10.dp,
    offsetY: Dp = 10.dp,
    offsetX: Dp = 10.dp
) = this.drawBehind {

    val transparentColor = android.graphics.Color.toArgb(color.copy(alpha = 0.0f).value.toLong())
    val shadowColor = android.graphics.Color.toArgb(color.copy(alpha = alpha).value.toLong())

    this.drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            borderRadius.toPx(),
            borderRadius.toPx(),
            paint
        )
    }
}