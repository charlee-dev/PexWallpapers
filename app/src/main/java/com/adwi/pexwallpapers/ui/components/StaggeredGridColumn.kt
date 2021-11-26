package com.adwi.pexwallpapers.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import kotlin.math.max

@Composable
fun StaggeredGridColumn(
    modifier: Modifier = Modifier,
    columns: Int = 2,
    content: @Composable () -> Unit,
) {
    Layout(content = content, modifier = modifier) { measurables, constraints ->
        val columnWidths = IntArray(columns) { 0 }
        val columnHeights = IntArray(columns) { 0 }

        val placables = measurables.mapIndexed { index, measurable ->
            val placable = measurable.measure(constraints)

            val col = index % columns
            columnHeights[col] += placable.height
            columnWidths[col] = max(columnWidths[col], placable.width)
            placable
        }

        val height = columnHeights.maxOrNull()
            ?.coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))
            ?: constraints.minHeight

        val width =
            columnWidths.sumOf { it }.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth))

        val colX = IntArray(columns) { 0 }
        for (i in 1 until columns) {
            colX[i] = colX[i - 1] + columnWidths[i - 1]
        }

        layout(width, height) {
            val colY = IntArray(columns) { 0 }
            placables.forEachIndexed { index, placeable ->
                val col = index % columns
                placeable.placeRelative(
                    x = colX[col],
                    y = colY[col]
                )
                colY[col] += placeable.height
            }
        }
    }
}