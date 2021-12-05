package com.adwi.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues

@Composable
fun Header(
    modifier: Modifier = Modifier,
    hasUpPress: Boolean = false,
    onUpPress: () -> Unit = {},
    title: String = stringResource(id = R.string.app_name),
    icon: ImageVector = Icons.Outlined.Home,
    actionIcon: ImageVector? = Icons.Outlined.Search,
    onActionClick: () -> Unit = {},
    shape: Shape = MaterialTheme.shapes.large,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.onBackground
) {
    Card(
        shape = shape,
        backgroundColor = backgroundColor,
        modifier = Modifier
            .padding(paddingValues)
            .height(70.dp)
            .neumorphicShadow(offset = (-5).dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues / 2)
        ) {
            if (hasUpPress) {
                IconButton(
                    onClick = onUpPress,
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBackIos,
                        contentDescription = stringResource(R.string.back),
                        tint = contentColor
                    )
                }
            }
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
            actionIcon?.let {
                IconButton(
                    onClick = onActionClick
                ) {
                    Icon(
                        imageVector = actionIcon,
                        contentDescription = stringResource(id = R.string.search),
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                    )
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
            Header(hasUpPress = false, onActionClick = {})
            Spacer(modifier = Modifier.size(paddingValues))
            Header(hasUpPress = true, onActionClick = {})
            Spacer(modifier = Modifier.size(paddingValues))
            Header(hasUpPress = false, onActionClick = {})
            Spacer(modifier = Modifier.size(paddingValues))
            Header(hasUpPress = true, onActionClick = {})
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
            Header(hasUpPress = false, onActionClick = {})
            Spacer(modifier = Modifier.size(paddingValues))
            Header(hasUpPress = true, onActionClick = {})
            Spacer(modifier = Modifier.size(paddingValues))
            Header(hasUpPress = false, onActionClick = {})
            Spacer(modifier = Modifier.size(paddingValues))
            Header(hasUpPress = true, onActionClick = {})
        }
    }
}