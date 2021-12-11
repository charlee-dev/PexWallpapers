package com.adwi.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
    hasMoreButton: Boolean = true,
    onMoreClick: () -> Unit = {},
    expanded: Boolean = true,
    menuItems: @Composable () -> Unit
) {
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
                modifier = modifier.fillMaxSize()
            ) {
                if (hasUpPress) {
                    IconButton(
                        onClick = onUpPress,
                        modifier = Modifier
                            .background(MaterialTheme.colors.primaryVariant)
                            .fillMaxHeight()
                            .padding(start = 4.dp, end = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIos,
                            contentDescription = stringResource(R.string.back),
                            tint = buttonsColor
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = paddingValues / 2)
                        .padding(top = paddingValues / 2)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier
                            .padding(
                                start = paddingValues / 2,
                                end = paddingValues / 2
                            )
                    )
                    Text(
                        text = title,
                        color = contentColor
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    if (hasMoreButton) {
                        IconButton(
                            onClick = onMoreClick
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.MoreVert,
                                contentDescription = stringResource(id = R.string.menu),
                                tint = buttonsColor,
                                modifier = Modifier
                            )
                        }
                    }
                }
            }
            AnimatedVisibility(expanded) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth(.8f)
                            .height((1).dp)
                            .background(MaterialTheme.colors.secondary)
                    )
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .padding(paddingValues)
                                .align(Alignment.Center)
                        ) {
                            menuItems()
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.size(paddingValues / 2))
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
                hasMoreButton = true,
                menuItems = {}
            )
            Spacer(modifier = Modifier.size(paddingValues))
            PexExpandableAppBar(
                hasUpPress = true,
                showShadows = true,
                hasMoreButton = true,
                menuItems = {}
            )
            Spacer(modifier = Modifier.size(paddingValues))
            PexExpandableAppBar(
                hasUpPress = false,
                showShadows = true,
                hasMoreButton = true,
                menuItems = {}
            )
            Spacer(modifier = Modifier.size(paddingValues))
            PexExpandableAppBar(
                hasUpPress = true,
                showShadows = true,
                hasMoreButton = true,
                menuItems = {}
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
                hasMoreButton = true,
                menuItems = {}
            )
            Spacer(modifier = Modifier.size(paddingValues))
            PexExpandableAppBar(
                hasUpPress = true,
                showShadows = true,
                hasMoreButton = true,
                menuItems = {}
            )
            Spacer(modifier = Modifier.size(paddingValues))
            PexExpandableAppBar(
                hasUpPress = false,
                showShadows = true,
                hasMoreButton = true,
                menuItems = {}
            )
            Spacer(modifier = Modifier.size(paddingValues))
            PexExpandableAppBar(
                hasUpPress = true,
                showShadows = true,
                hasMoreButton = true,
                menuItems = {}
            )
        }
    }
}