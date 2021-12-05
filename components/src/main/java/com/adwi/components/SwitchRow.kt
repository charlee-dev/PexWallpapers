package com.adwi.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.adwi.components.theme.paddingValues

@ExperimentalMaterialApi
@Composable
fun SwitchRow(
    modifier: Modifier = Modifier,
    name: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    textColor: Color = MaterialTheme.colors.onBackground,
    switchEnabledColor: Color = MaterialTheme.colors.primary,
    switchDisabledColor: Color = MaterialTheme.colors.secondary,
    backgroundColor: Color = MaterialTheme.colors.surface,
) {
    Surface(
        onClick = {
            onCheckedChange(!checked)
        },
        modifier = Modifier.fillMaxWidth(),
        color = backgroundColor
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = paddingValues),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                modifier = Modifier.weight(1f),
                color = textColor
            )
            Switch(
                checked = checked,
                onCheckedChange = {
                    onCheckedChange(it)
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = switchEnabledColor,
                    uncheckedThumbColor = switchDisabledColor
                )
            )
        }
    }
}