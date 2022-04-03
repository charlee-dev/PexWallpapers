package com.adwi.feature_settings.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Message
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adwi.components.neumorphicShadow
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues

@ExperimentalMaterialApi
@Composable
fun InfoRow(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    title: String,
    icon: ImageVector,
    showShadows: Boolean
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.background
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                shape = RoundedCornerShape(30),
                modifier = Modifier
                    .padding(paddingValues / 2)
                    .neumorphicShadow(
                        enabled = showShadows,
                        isPressed = isPressed,
                        cornerRadius = 10.dp,
                        offset = (-5).dp
                    ),
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Box {
                    Icon(
                        imageVector = icon,
                        tint = MaterialTheme.colors.secondary,
                        contentDescription = title,
                        modifier = Modifier
                            .padding(paddingValues / 2)
                            .align(Alignment.Center)
                    )
                }

            }
            Text(
                text = title,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = paddingValues / 2),
                color = MaterialTheme.colors.primary
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true, name = "Light")
@Composable
private fun InfoRowPreviewLight() {
    PexWallpapersTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            InfoRow(
                onClick = { /*TODO*/ },
                title = "About Us",
                icon = Icons.Outlined.Message,
                showShadows = true
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true, name = "Dark")
@Composable
private fun InfoRowPreviewDark() {
    PexWallpapersTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            InfoRow(
                onClick = { /*TODO*/ },
                title = "About Us",
                icon = Icons.Outlined.Message,
                showShadows = true
            )
        }
    }
}