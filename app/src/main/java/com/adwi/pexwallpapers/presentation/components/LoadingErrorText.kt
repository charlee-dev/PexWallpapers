package com.adwi.pexwallpapers.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.adwi.pexwallpapers.R
import com.adwi.pexwallpapers.presentation.theme.PexWallpapersTheme
import com.adwi.pexwallpapers.presentation.theme.paddingValues

@ExperimentalMaterialApi
@Composable
fun LoadingErrorText(
    modifier: Modifier = Modifier,
    visible: Boolean,
    message: String,
    textColor: Color = MaterialTheme.colors.onPrimary
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = message,
                color = textColor,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = paddingValues / 2)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_swipe_down),
                contentDescription = stringResource(R.string.swipe_down_to_refresh),
                tint = textColor
            )
            Text(text = stringResource(id = R.string.swipe_down_to_refresh), color = textColor)
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true, name = "Light")
@Composable
private fun LoadingErrorTextPreviewLight() {
    PexWallpapersTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.primary)
                .padding(paddingValues)
        ) {
            LoadingErrorText(
                visible = true,
                message = stringResource(id = R.string.unknown_error_occurred)
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true, name = "Dark")
@Composable
private fun LoadingErrorTextPreviewDark() {
    PexWallpapersTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.primary)
                .padding(paddingValues)
        ) {
            LoadingErrorText(
                visible = true,
                message = stringResource(id = R.string.unknown_error_occurred)
            )
        }
    }
}