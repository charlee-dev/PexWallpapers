package com.adwi.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.adwi.components.theme.MenuItem
import com.adwi.components.theme.MenuItems
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues

@ExperimentalMaterialApi
@Composable
fun MenuListItem(
    modifier: Modifier = Modifier,
    action: () -> Unit,
    item: MenuItem
) {
    Surface(onClick = action, modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
            Icon(imageVector = item.icon, contentDescription = stringResource(id = item.name))
            Spacer(modifier = Modifier.size(paddingValues))
            Text(
                text = stringResource(id = item.name),
                style = MaterialTheme.typography.subtitle1.copy(color = MaterialTheme.colors.onSurface)
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true, name = "MenuListItem - Light")
@Composable
fun MenuListItemPreview() {
    PexWallpapersTheme(darkTheme = false) {
        Column(modifier = Modifier.padding(paddingValues)) {
            MenuListItem(
                item = MenuItems.Search,
                action = {}
            )
        }
    }
}