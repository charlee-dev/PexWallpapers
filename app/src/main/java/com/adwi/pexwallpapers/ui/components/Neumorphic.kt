package com.adwi.pexwallpapers.ui.components

import android.annotation.SuppressLint
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adwi.pexwallpapers.ui.theme.PrimaryShadowDark
import com.adwi.pexwallpapers.ui.theme.PrimaryShadowLight

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.neumorphicShadow(
    colorTop: Color = PrimaryShadowLight,
    colorBottom: Color = PrimaryShadowDark,
    alpha: Float = .9f,
    cornerRadius: Dp = 30.dp,
    shadowRadius: Dp = 10.dp,
    offset: Dp = (-10).dp,
) = this.drawBehind {

    val transparentColor = android.graphics.Color.toArgb(colorTop.copy(alpha = 0.0f).value.toLong())
    val shadowColorTop = android.graphics.Color.toArgb(colorTop.copy(alpha = alpha).value.toLong())
    val shadowColorBottom =
        android.graphics.Color.toArgb(colorBottom.copy(alpha = alpha).value.toLong())

    this.drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowRadius.toPx(),
            offset.toPx(),
            offset.toPx(),
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