package com.adwi.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.adwi.components.theme.paddingValues

@Composable
fun Header(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.app_name),
    icon: ImageVector = Icons.Outlined.Home,
    actionIcon: ImageVector? = Icons.Outlined.Search,
    onActionClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = paddingValues / 2)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f),

            ) {

            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .padding(horizontal = paddingValues / 2)
            )
            Text(
                text = title,
                color = MaterialTheme.colors.onBackground
            )
        }
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

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    MaterialTheme {
        Header(onActionClick = {})
    }
}