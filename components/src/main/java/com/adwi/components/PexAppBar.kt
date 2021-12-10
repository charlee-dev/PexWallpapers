package com.adwi.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
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
fun PexAppBar(
    modifier: Modifier = Modifier,
    hasUpPress: Boolean = false,
    onUpPress: () -> Unit = {},
    title: String = stringResource(id = R.string.app_name),
    icon: ImageVector = Icons.Outlined.Home,
    hasMoreButton: Boolean = true,
    onMoreClick: () -> Unit = {},
    shape: Shape = MaterialTheme.shapes.large,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.onBackground,
    buttonsColor: Color = MaterialTheme.colors.primary,
    showShadows: Boolean
) {
    Card(
        shape = shape,
        backgroundColor = backgroundColor,
        modifier = Modifier
            .padding(horizontal = paddingValues)
            .padding(bottom = paddingValues / 2, top = paddingValues)
            .height(70.dp)
            .neumorphicShadow(enabled = showShadows, offset = (-5).dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxSize()
        ) {
            if (hasUpPress) {
                IconButton(
                    onClick = onUpPress,
                    modifier = Modifier
                        .background(MaterialTheme.colors.primaryVariant)
                        .fillMaxHeight()
                        .padding(start = paddingValues / 2, end = 6.dp)
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
            PexAppBar(
                hasUpPress = false,
                showShadows = true,
                hasMoreButton = true,
            )
            Spacer(modifier = Modifier.size(paddingValues))
            PexAppBar(
                hasUpPress = true,
                showShadows = true,
                hasMoreButton = true,
            )
            Spacer(modifier = Modifier.size(paddingValues))
            PexAppBar(
                hasUpPress = false,
                showShadows = true,
                hasMoreButton = true,
            )
            Spacer(modifier = Modifier.size(paddingValues))
            PexAppBar(
                hasUpPress = true,
                showShadows = true,
                hasMoreButton = true,
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
            PexAppBar(
                hasUpPress = false,
                showShadows = true,
                hasMoreButton = true,
            )
            Spacer(modifier = Modifier.size(paddingValues))
            PexAppBar(
                hasUpPress = true,
                showShadows = true,
                hasMoreButton = true,
            )
            Spacer(modifier = Modifier.size(paddingValues))
            PexAppBar(
                hasUpPress = false,
                showShadows = true,
                hasMoreButton = true,
            )
            Spacer(modifier = Modifier.size(paddingValues))
            PexAppBar(
                hasUpPress = true,
                showShadows = true,
                hasMoreButton = true,
            )
        }
    }
}