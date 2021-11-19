package com.adwi.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.CategoryPanel
import com.adwi.components.Header
import com.adwi.components.theme.Dimensions.BottomBar.BottomNavHeight
import com.adwi.components.theme.paddingValues
import com.adwi.home.SettingsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onTriggerEvent: (SettingsEvent) -> Unit
//    onWallpaperClick: (Int) -> Unit,
//    onCategoryClick: () -> Unit,
) {
    Column() {
        Text("SettingsScreen")
    }
    val settings by viewModel.settings.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            bottom = BottomNavHeight + paddingValues
        )
    ) {
        item {
            Header()
        }
        item {
            SettingPanel(
                modifier = Modifier,
                name = stringResource(id = R.string.new_wallpaper_set),
                checked = settings.newWallpaperSet,
                onCheckedChange = { onTriggerEvent(SettingsEvent.UpdateNewWallpaperSet(it)) }
            ) {
                SettingCheckboxRow(name =, checked =, onCheckedChange =)
                // TODO(add more rows)
            }
        }

    }
}

@ExperimentalMaterialApi
@Composable
private fun SettingPanel(
    modifier: Modifier = Modifier,
    name: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(checked) }
    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = paddingValues)
            .animateContentSize()
    ) {
        CategoryPanel(categoryName = name)
    }
    Card() {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            SettingCheckboxRow(
                name = stringResource(id = R.string.new_wallpaper_set),
                checked = expanded,
                onCheckedChange = {
                    onCheckedChange(it)
                    expanded = !expanded
                }
            )
            AnimatedVisibility(expanded) {
                content()
            }
        }
    }
}

@Composable
private fun SettingCheckboxRow(
    modifier: Modifier = Modifier,
    name: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    var state by remember { mutableStateOf(checked) }

    Row(modifier = modifier.fillMaxWidth()) {
        Text(
            text = name,
            modifier = Modifier.weight(1f)
        )
        Checkbox(
            checked = state,
            onCheckedChange = {
                state = !state
                onCheckedChange(state)
            }
        )
    }
}