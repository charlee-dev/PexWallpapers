package com.adwi.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.CategoryPanel
import com.adwi.components.Header
import com.adwi.components.theme.Dimensions.BottomBar.BottomNavHeight
import com.adwi.components.theme.PrimaryLight
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
    val settings by viewModel.settings.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = paddingValues),
        contentPadding = PaddingValues(
            bottom = BottomNavHeight + paddingValues
        )
    ) {
        item {
            Header(
                title = stringResource(id = R.string.settings),
                icon = Icons.Outlined.Settings,
                actionIcon = Icons.Outlined.Refresh,
                onActionClick = { onTriggerEvent(SettingsEvent.ResetSettings) }
            )
        }
        item {
            SettingPanel(
                modifier = Modifier,
                panelName = stringResource(id = R.string.notifications),
                mainName = stringResource(id = R.string.push_notifications),
                checked = settings.newWallpaperSet,
                onCheckedChange = { onTriggerEvent(SettingsEvent.UpdateNewWallpaperSet(it)) }
            ) {
                SwitchRow(
                    name = stringResource(id = R.string.new_wallpaper_set),
                    checked = settings.newWallpaperSet,
                    onCheckedChange = { onTriggerEvent(SettingsEvent.UpdateNewWallpaperSet(it)) }
                )
                SwitchRow(
                    name = stringResource(id = R.string.wallpaper_recomendations),
                    checked = settings.wallpaperRecommendations,
                    onCheckedChange = {
                        onTriggerEvent(
                            SettingsEvent.UpdateWallpaperRecommendations(
                                it
                            )
                        )
                    }
                )
            }
        }
        item {
            SettingPanel(
                modifier = Modifier,
                panelName = stringResource(id = R.string.automation),
                mainName = stringResource(id = R.string.auto_change_wallpaper),
                checked = settings.autoChangeWallpaper,
                onCheckedChange = { onTriggerEvent(SettingsEvent.UpdateAutoChangeWallpaper(it)) }
            ) {
                HomeOrLockRow(
                    homeState = settings.autoHome,
                    onHomeChange = { onTriggerEvent(SettingsEvent.UpdateAutoHome(it)) },
                    lockState = settings.autoLock,
                    onLockChange = { onTriggerEvent(SettingsEvent.UpdateAutoLock(it)) }
                )
            }
        }
    }
}

@Composable
private fun HomeOrLockRow(
    modifier: Modifier = Modifier,
    homeState: Boolean,
    onHomeChange: (Boolean) -> Unit,
    lockState: Boolean,
    onLockChange: (Boolean) -> Unit,
) {
    var home by remember { mutableStateOf(homeState) }
    var lock by remember { mutableStateOf(lockState) }
    Column(
        Modifier
            .fillMaxWidth()
            .padding(vertical = paddingValues / 2)
    ) {
        Text(
            text = stringResource(id = com.adwi.composables.R.string.screen_to_change) + ":"
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = paddingValues)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = home, onCheckedChange = {
                        home = !home
                        onHomeChange(home)
                    })
                Text(
                    text = stringResource(id = R.string.home_screen),
                    modifier = Modifier.padding(start = paddingValues / 2)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = lock,
                    onCheckedChange = {
                        lock = !lock
                        onLockChange(lock)
                    })
                Text(
                    text = stringResource(id = R.string.lock_screen),
                    modifier = Modifier.padding(start = paddingValues / 2)
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun SettingPanel(
    modifier: Modifier = Modifier,
    panelName: String,
    mainName: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    shape: Shape = MaterialTheme.shapes.large,
    content: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(checked) }
    val transition = updateTransition(targetState = expanded, label = "Card")
    val elevation by transition.animateDp(label = "Card elevation") { state ->
        if (state) 10.dp else 2.dp
    }

    Column(
        modifier.fillMaxWidth()
    ) {
        CategoryPanel(categoryName = panelName)
    }
    Card(
        elevation = elevation,
        shape = shape
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = paddingValues, vertical = paddingValues / 2)
        ) {
            SwitchRow(
                name = mainName,
                checked = expanded,
                onCheckedChange = {
                    onCheckedChange(it)
                    expanded = !expanded
                },
                modifier = Modifier.background(PrimaryLight)
            )
            AnimatedVisibility(expanded) {
                Column(Modifier.fillMaxWidth()) {
                    content()
                }
            }
        }
    }
}

@Composable
private fun SwitchRow(
    modifier: Modifier = Modifier,
    name: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    var state by remember { mutableStateOf(checked) }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = state,
            onCheckedChange = {
                state = !state
                onCheckedChange(state)
            }
        )
    }
}