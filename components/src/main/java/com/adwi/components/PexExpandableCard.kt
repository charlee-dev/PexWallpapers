package com.adwi.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues

@ExperimentalMaterialApi
@Composable
fun PexExpandableCard(
    modifier: Modifier = Modifier,
    headerText: String,
    shape: Shape = MaterialTheme.shapes.large,
    showShadows: Boolean,
    content: @Composable () -> Unit
) {
    // Pressed state
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val pressed = updateTransition(targetState = isPressed, label = "Card")
    val cardScale by pressed.animateFloat(label = "Card scale") { state ->
        if (state) .99f else 1f
    }

    // Expanded state
    var checked by rememberSaveable { mutableStateOf(false) }
    val expanded = updateTransition(targetState = checked, label = "Card")
    val headerBackgroundColor by expanded.animateColor(label = "Card header background color") { state ->
        if (state) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
    }
    val headerTextColor by expanded.animateColor(label = "Card header text color") { state ->
        if (state) MaterialTheme.colors.secondary else MaterialTheme.colors.onBackground
    }
    val headerIconColor by expanded.animateColor(label = "Card header enabled icon color") { state ->
        if (state) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.primary
    }
    val headerIconRotation by expanded.animateFloat(label = "Icon") { state ->
        if (state) 180f else 0f
    }
    val contentAlpha by expanded.animateFloat(label = "Content alpha") { state ->
        if (state) 1f else 0f
    }

    Card(
        shape = shape,
        modifier = Modifier
            .scale(cardScale)
            .neumorphicShadow(enabled = showShadows)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            Surface(
                onClick = { checked = !checked },
                color = headerBackgroundColor,
                indication = null,
                interactionSource = interactionSource,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(headerBackgroundColor)
                    .padding(top = paddingValues / 2)
                    .padding(bottom = paddingValues / 2),
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = paddingValues),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = headerText,
                        modifier = Modifier.weight(1f),
                        color = headerTextColor
                    )
                    Icon(
                        imageVector = Icons.Outlined.ExpandMore,
                        contentDescription = stringResource(id = R.string.show_more),
                        modifier = Modifier.rotate(headerIconRotation),
                        tint = headerIconColor
                    )
                }
            }
            AnimatedVisibility(checked) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .alpha(contentAlpha)
                ) {
                    Spacer(modifier = Modifier.size(paddingValues / 2))
                    content()
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true, name = "Light")
@Composable
private fun PexExpandableCardPreviewLight() {
    Column {
        PexWallpapersTheme(darkTheme = false) {
            Column(
                Modifier
                    .background(MaterialTheme.colors.background)
                    .padding(paddingValues)
            ) {
                PexExpandableCard(
                    headerText = "Push notifications",
                    showShadows = true
                ) {
                    SwitchRow(name = "test row", checked = true, onCheckedChange = {})
                    SwitchRow(name = "test row", checked = false, onCheckedChange = {})
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true, name = "Dark")
@Composable
private fun PexExpandableCardPreviewDark() {
    PexWallpapersTheme(darkTheme = true) {
        Column(
            Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            PexExpandableCard(
                headerText = "Push notifications",
                showShadows = true
            ) {
                SwitchRow(name = "test row", checked = true, onCheckedChange = {})
                SwitchRow(name = "test row", checked = false, onCheckedChange = {})
            }
        }
    }
}