package com.adwi.feature_settings.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import com.adwi.components.CategoryPanel
import com.adwi.components.neumorphicShadow
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues


@ExperimentalMaterialApi
@Composable
fun SettingPanel(
    modifier: Modifier = Modifier,
    panelName: String,
    mainName: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    shape: Shape = MaterialTheme.shapes.large,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val transition = updateTransition(targetState = checked, label = "Card")

    val backgroundColor by transition.animateColor(label = "Card background color") { state ->
        if (state) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
    }
    val textColor by transition.animateColor(label = "Card text color") { state ->
        if (state) MaterialTheme.colors.secondary else MaterialTheme.colors.onBackground
    }
    val switchEnabledColor by transition.animateColor(label = "Card enabled switch color") { state ->
        if (state) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.primary
    }

    Column(
        modifier.fillMaxWidth()
    ) {
        CategoryPanel(categoryName = panelName)
        Card(
            shape = shape,
            modifier = Modifier.neumorphicShadow(pressed = isPressed)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
            ) {
                SwitchRow(
                    name = mainName,
                    checked = checked,
                    onCheckedChange = {
                        onCheckedChange(it)
                    },
                    modifier = Modifier
                        .background(backgroundColor)
                        .padding(top = paddingValues / 2)
                        .padding(bottom = paddingValues / 2),
                    textColor = textColor,
                    switchEnabledColor = switchEnabledColor,
                    switchDisabledColor = MaterialTheme.colors.primary
                )
                AnimatedVisibility(checked) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.size(paddingValues / 2))
                        content()
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true, name = "Light")
@Composable
private fun SettingsPanelPreview() {
    Column() {
        PexWallpapersTheme(darkTheme = false) {
            Column(
                Modifier
                    .background(MaterialTheme.colors.background)
                    .padding(paddingValues)
            ) {
                SettingPanel(
                    panelName = "Light collapsed",
                    mainName = "Push notifications",
                    checked = false,
                    onCheckedChange = {},
                    content = {}
                )
                Spacer(modifier = Modifier.size(paddingValues))
                SettingPanel(
                    panelName = "Light expanded",
                    mainName = "Push notifications",
                    checked = true,
                    onCheckedChange = {},
                    content = {
                        SwitchRow(name = "test row", checked = true, onCheckedChange = {})
                        SwitchRow(name = "test row", checked = false, onCheckedChange = {})
                    }
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true, name = "Dark")
@Composable
private fun SettingsPanelPreviewDark() {
    PexWallpapersTheme(darkTheme = true) {
        Column(
            Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            SettingPanel(
                panelName = "Dark collapsed",
                mainName = "Push notifications",
                checked = false,
                onCheckedChange = {},
                content = {}
            )
            Spacer(modifier = Modifier.size(paddingValues))
            SettingPanel(
                panelName = "Dark expanded",
                mainName = "Push notifications",
                checked = true,
                onCheckedChange = {}
            ) {
                SwitchRow(name = "test row", checked = true, onCheckedChange = {})
                SwitchRow(name = "test row", checked = false, onCheckedChange = {})
            }
        }
    }
}