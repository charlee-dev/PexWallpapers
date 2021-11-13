package com.adwi.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.Save
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.adwi.components.theme.paddingValues

@Composable
fun ImageActionButtons(
    modifier: Modifier = Modifier,
    onUrlClick: (String) -> Unit,
    onSaveClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ActionButton(
            icon = Icons.Outlined.Link
        )
        ActionButton(
            icon = Icons.Outlined.Save
        )
        ActionButton(
            icon = Icons.Outlined.Bookmark
        )
    }
}

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Outlined.Save,
    tint: Color = MaterialTheme.colors.onBackground,
    contentDescription: String = ""
) {
    IconButton(
        onClick = {}
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint,
            modifier = modifier
                .padding(horizontal = paddingValues / 2)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ImageActionButtonsPreview() {
    MaterialTheme {
        ImageActionButtons(onUrlClick = {}, onSaveClick = {}, onFavoriteClick = {})
    }
}