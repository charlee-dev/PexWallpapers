package com.adwi.feature_settings.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.adwi.components.theme.paddingValues

@Composable
fun OptionTip(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colors.onBackground,
    enabled: Boolean = true
) {
    Text(
        text = text,
        style = MaterialTheme.typography.caption,
        color = if (enabled) color else color.copy(alpha = .5f),
        modifier = modifier
            .padding(horizontal = paddingValues)
            .padding(end = paddingValues, bottom = paddingValues / 2)
    )
}