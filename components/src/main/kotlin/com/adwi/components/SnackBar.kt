package com.adwi.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adwi.components.theme.Dimensions
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun PexSnackBar(
    snackBarData: SnackbarData,
    modifier: Modifier = Modifier,
    actionOnNewLine: Boolean = false,
    shape: Shape = MaterialTheme.shapes.large,
    backgroundColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = MaterialTheme.colors.background,
    actionColor: Color = MaterialTheme.colors.primaryVariant,
    elevation: Dp = 10.dp
) {

    Snackbar(
        snackbarData = snackBarData,
        modifier = modifier,
        actionOnNewLine = actionOnNewLine,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        actionColor = actionColor,
        elevation = elevation
    )
}

@Composable
fun PexSnackBarHost(
    modifier: Modifier = Modifier,
    hostState: SnackbarHostState
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier
            .systemBarsPadding()
            .wrapContentWidth(align = Alignment.Start)
            .widthIn(max = 550.dp)
            .padding(bottom = Dimensions.BottomBar.BottomNavHeight),
        snackbar = { snackbarData -> PexSnackBar(snackbarData) }
    )
}