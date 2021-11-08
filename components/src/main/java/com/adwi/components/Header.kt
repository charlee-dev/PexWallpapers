package com.adwi.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.adwi.components.theme.Dimensions
import com.adwi.composables.R

@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CategoryTitle(name = stringResource(id = R.string.wallpapers))
        IconButton(
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Outlined.Menu,
                contentDescription = stringResource(id = R.string.menu),
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .padding(horizontal = Dimensions.medium)
            )
        }
    }
}