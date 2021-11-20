package com.adwi.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.CategoryPanel
import com.adwi.components.Header
import com.adwi.components.theme.Dimensions.BottomBar.BottomNavHeight
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues
import com.adwi.domain.Duration
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

@ExperimentalMaterialApi
@Composable
private fun SettingPanel(
    modifier: Modifier = Modifier,
    panelName: String,
    mainName: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    shape: Shape = MaterialTheme.shapes.large,
    background: Color = MaterialTheme.colors.surface,
    content: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(checked) }
    val transition = updateTransition(targetState = expanded, label = "Card")

    val elevation by transition.animateDp(label = "Card elevation") { state ->
        if (state) 10.dp else 2.dp
    }

    val backgroundColor by transition.animateColor(label = "Card background color") { state ->
        if (state) MaterialTheme.colors.primary else background
    }
    val textColor by transition.animateColor(label = "Card text color") { state ->
        if (state) MaterialTheme.colors.surface else MaterialTheme.colors.onSurface
    }
    val switchColor by transition.animateColor(label = "Card switch color") { state ->
        if (state) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.primary
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
        ) {
            SwitchRow(
                name = mainName,
                checked = expanded,
                onCheckedChange = {
                    onCheckedChange(it)
                    expanded = !expanded
                },
                modifier = Modifier
                    .background(backgroundColor)
                    .padding(top = paddingValues / 2)
                    .padding(bottom = paddingValues / 2),
                textColor = textColor,
                switchColor = switchColor
            )
            AnimatedVisibility(expanded) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = paddingValues / 2)
                ) {
                    content()
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun SwitchRow(
    modifier: Modifier = Modifier,
    name: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    textColor: Color = MaterialTheme.colors.onSurface,
    switchColor: Color = MaterialTheme.colors.primary,
    backgroundColor: Color = MaterialTheme.colors.surface,
) {
    var state by remember { mutableStateOf(checked) }

    Surface(
        onClick = {
            state = !state
            onCheckedChange(state)
        },
        modifier = Modifier.fillMaxWidth(),
        color = backgroundColor
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = paddingValues),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                modifier = Modifier.weight(1f),
                color = textColor
            )
            Switch(
                checked = state,
                onCheckedChange = {
                    state = it
                    onCheckedChange(it)
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = switchColor
                )
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun HomeOrLockRow(
    modifier: Modifier = Modifier,
    homeState: Boolean,
    onHomeChange: (Boolean) -> Unit,
    lockState: Boolean,
    onLockChange: (Boolean) -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = com.adwi.composables.R.string.screen_to_change) + ":",
            modifier = Modifier.padding(horizontal = paddingValues)
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.size(paddingValues / 2))
            CheckBoxRow(
                text = stringResource(id = R.string.home_screen),
                state = homeState,
                onStateChange = { onHomeChange(it) }
            )
            CheckBoxRow(
                text = stringResource(id = R.string.lock_screen),
                state = lockState,
                onStateChange = { onLockChange(it) }
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun CheckBoxRow(
    modifier: Modifier = Modifier,
    text: String,
    state: Boolean,
    onStateChange: (Boolean) -> Unit,
    backgroundColor: Color = MaterialTheme.colors.surface
) {
    var checked by remember { mutableStateOf(state) }

    Surface(
        onClick = {
            checked = !checked
            onStateChange(checked)
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
                    checked = !checked
                    onStateChange(checked)
                })
            Text(
                text = text,
                modifier = Modifier.padding(start = paddingValues / 2)
            )
        }
    }
}

@Composable
private fun DurationPanel(
    modifier: Modifier = Modifier,
    text: String,
    duration: Duration,
    onDurationChange: (Boolean) -> Unit,
    backgroundColor: Color = MaterialTheme.colors.surface
) {
    val radioOptions = listOf(Duration.MINUTE, Duration.HOUR, Duration.DAY, Duration.WEEK)

    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[1]) }

    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.change_wallpaper_every) + ":",
            modifier = Modifier.padding(horizontal = paddingValues)
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.size(paddingValues / 2))

            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)
                            }
                        )
                        .padding(horizontal = 16.dp)
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) }
                    )
                    Text(
                        text = text.name,
                        style = MaterialTheme.typography.body1.merge(),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun RadioItem(
    modifier: Modifier = Modifier,
    text: String,

    ) {

}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
private fun SwitchRowPreview() {
    PexWallpapersTheme {
        SwitchRow(name = "Auto wallpaper change", checked = true, onCheckedChange = {})
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
private fun HomeOrLockRowPreview() {
    PexWallpapersTheme {
        HomeOrLockRow(homeState = true, onHomeChange = {}, lockState = false, onLockChange = {})
    }
}