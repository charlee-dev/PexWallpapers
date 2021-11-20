package com.adwi.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adwi.components.theme.paddingValues

@Composable
fun ButtonRoundedTop(
    modifier: Modifier = Modifier,
    onSetWallpaperClick: () -> Unit,
    text: String,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.onSurface
) {
    Button(
        onClick = onSetWallpaperClick,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        )

    ) {
        Text(text = text)
    }
}


@ExperimentalMaterialApi
@Composable
fun PexButton(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    text: String,
    shape: Shape = MaterialTheme.shapes.large,
    enabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colors.primaryVariant,
    contentColor: Color = MaterialTheme.colors.onSurface,
    textColor: Color = MaterialTheme.colors.primary,
    onClick: () -> Unit = {}
) {
    Button(
        enabled = enabled,
        onClick = { onClick() },
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        ),
        modifier = modifier
    ) {
        if (icon != null) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        Text(
            text = text.uppercase(),
            modifier = Modifier.padding(
                start = if (icon == null) paddingValues else 0.dp,
                end = paddingValues,
                top = paddingValues / 2,
                bottom = paddingValues / 2
            ),
            color = textColor
        )
    }
}

@ExperimentalMaterialApi
@Composable
@Preview(showBackground = true)
private fun PexButtonPreview() {
    Column {
        PexButton(
            text = "Add barber"
        )
    }
}