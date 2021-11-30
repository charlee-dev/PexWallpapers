package com.adwi.feature_settings.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues
import com.adwi.feature_settings.R

@ExperimentalMaterialApi
@Composable
fun HomeOrLockRow(
    modifier: Modifier = Modifier,
    homeChecked: Boolean,
    onHomeCheckedChange: (Boolean) -> Unit,
    lockChecked: Boolean,
    onLockCheckedChange: (Boolean) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.screen_to_change) + ":",
            modifier = Modifier.padding(horizontal = paddingValues),
            color = MaterialTheme.colors.onBackground
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.size(paddingValues / 2))
            CheckBoxRow(
                name = stringResource(id = R.string.home_screen),
                checked = homeChecked,
                onCheckedChange = { onHomeCheckedChange(it) }
            )
            CheckBoxRow(
                name = stringResource(id = R.string.lock_screen),
                checked = lockChecked,
                onCheckedChange = { onLockCheckedChange(it) }
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun CheckBoxRow(
    modifier: Modifier = Modifier,
    name: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    backgroundColor: Color = MaterialTheme.colors.surface
) {
    Surface(
        onClick = {
            onCheckedChange(!checked)
        }, modifier = Modifier.fillMaxWidth(),
        color = backgroundColor
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(horizontal = paddingValues * 2)
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = {
                    onCheckedChange(it)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colors.secondary,
                    checkmarkColor = MaterialTheme.colors.primary
                ),
            )
            Text(
                text = name,
                modifier = Modifier.padding(start = paddingValues / 2)
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true, name = "Light")
@Composable
private fun HomeOrLockRowPreviewLight() {
    PexWallpapersTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            HomeOrLockRow(
                homeChecked = true,
                onHomeCheckedChange = {},
                lockChecked = false,
                onLockCheckedChange = {}
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true, name = "Dark")
@Composable
private fun HomeOrLockRowPreviewDark() {
    PexWallpapersTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            HomeOrLockRow(
                homeChecked = true,
                onHomeCheckedChange = {},
                lockChecked = false,
                onLockCheckedChange = {}
            )
        }
    }
}