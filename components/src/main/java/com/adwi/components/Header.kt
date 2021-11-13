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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.adwi.components.theme.paddingValues
import com.adwi.composables.R

@Composable
fun Header(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.app_name),
    onSearchClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = paddingValues)
            .padding(top = paddingValues / 2)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f),

            ) {
            IconButton(
                onClick = onSearchClick
            ) {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = title,
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .padding(horizontal = paddingValues / 2)
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1
                    .merge(TextStyle(color = MaterialTheme.colors.onBackground)),
            )
        }
        IconButton(
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = stringResource(id = R.string.search),
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier
//                    .padding(horizontal = Dimensions.medium)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    MaterialTheme {
        Header(onSearchClick = {})
    }
}