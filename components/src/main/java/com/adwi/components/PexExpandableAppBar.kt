package com.adwi.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adwi.components.theme.*

@Composable
fun PexExpandableAppBar(
    modifier: Modifier = Modifier,
    hasUpPress: Boolean = false,
    onUpPress: () -> Unit = {},
    title: String = stringResource(id = R.string.app_name),
    icon: ImageVector = Icons.Outlined.Home,
    shape: Shape = MaterialTheme.shapes.large,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.onBackground,
    buttonsColor: Color = MaterialTheme.colors.primary,
    showShadows: Boolean,
    menuItems: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = expanded, label = "Header")

    val backgroundColorState by transition.animateColor(label = "Color") { state ->
        if (state) MaterialTheme.colors.primary else backgroundColor
    }
    val contentColorState by transition.animateColor(label = "Color") { state ->
        if (state) MaterialTheme.colors.secondary else contentColor
    }
    val buttonColorState by transition.animateColor(label = "Color") { state ->
        if (state) MaterialTheme.colors.primaryVariant else contentColor
    }

    Card(
        shape = shape,
        backgroundColor = backgroundColor,
        modifier = Modifier
            .padding(horizontal = paddingValues)
            .padding(bottom = paddingValues / 2, top = paddingValues)
            .neumorphicShadow(enabled = showShadows, offset = (-5).dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.animateContentSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .background(backgroundColorState)
            ) {
                if (hasUpPress) {
                    IconButton(
                        onClick = onUpPress,
                        modifier = Modifier
                            .background(MaterialTheme.colors.primaryVariant)
                            .size(Dimensions.Button)
                            .padding(start = 4.dp, end = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIos,
                            contentDescription = stringResource(R.string.back),
                            tint = buttonsColor
                        )
                    }
                }
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = contentColorState,
                    modifier = Modifier
                        .padding(
                            start = if (hasUpPress) paddingValues / 2 else paddingValues,
                            end = paddingValues / 2
                        )
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.h6.copy(contentColorState)
                )
                Spacer(modifier = Modifier.weight(1f))
                ShowMenuButton(
                    onClick = { expanded = !expanded },
                    expanded = expanded,
                    buttonColor = buttonColorState
                )
            }
            AnimatedVisibility(expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.surface),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .padding(paddingValues / 2)
                                .align(Alignment.Center)
                        ) {
                            menuItems()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShowMenuButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    expanded: Boolean,
    buttonColor: Color
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(Dimensions.Button)
    ) {
        if (expanded) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = stringResource(id = R.string.hide_menu),
                tint = buttonColor,
                modifier = Modifier
            )
        } else {
            Icon(
                imageVector = Icons.Outlined.MoreVert,
                contentDescription = stringResource(id = R.string.show_menu),
                tint = buttonColor,
                modifier = Modifier
            )
        }
    }
}

@Preview(showBackground = true, name = "Header Light")
@Composable
fun HeaderPreviewLight() {
    PexWallpapersTheme(darkTheme = false) {
        Column(
            Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            PexExpandableAppBar(
                hasUpPress = false,
                showShadows = true,
                menuItems = {},
            )
            Spacer(modifier = Modifier.size(paddingValues))
            PexExpandableAppBar(
                hasUpPress = true,
                showShadows = true,
                menuItems = {},
            )
            Spacer(modifier = Modifier.size(paddingValues))
            PexExpandableAppBar(
                hasUpPress = false,
                showShadows = true,
                menuItems = {},
            )
            Spacer(modifier = Modifier.size(paddingValues))
            PexExpandableAppBar(
                hasUpPress = true,
                showShadows = true,
                menuItems = {},
            )
        }
    }
}

@Preview(showBackground = true, name = "Header Dark")
@Composable
fun HeaderPreviewDark() {
    PexWallpapersTheme(darkTheme = true) {
        Column(
            Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            PexExpandableAppBar(
                hasUpPress = false,
                showShadows = true,
                menuItems = {},
            )
            Spacer(modifier = Modifier.size(paddingValues))
            PexExpandableAppBar(
                hasUpPress = true,
                showShadows = true,
                menuItems = {},
            )
            Spacer(modifier = Modifier.size(paddingValues))
            PexExpandableAppBar(
                hasUpPress = false,
                showShadows = true,
                menuItems = {},
            )
            Spacer(modifier = Modifier.size(paddingValues))
            PexExpandableAppBar(
                hasUpPress = true,
                showShadows = true,
                menuItems = {},
            )
        }
    }
}