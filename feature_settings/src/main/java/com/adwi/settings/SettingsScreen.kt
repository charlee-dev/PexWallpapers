package com.adwi.settings

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.CategoryPanel
import com.adwi.components.Header
import com.adwi.components.PexScaffold
import com.adwi.components.theme.Dimensions.BottomBar.BottomNavHeight
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues
import com.adwi.core.domain.Event
import com.adwi.home.SettingsViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalPermissionsApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
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
    val days by viewModel.days.collectAsState()
    val hours by viewModel.hours.collectAsState()
    val minutes by viewModel.minutes.collectAsState()

    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current



    PexScaffold(
        viewModel = viewModel,
        scaffoldState = scaffoldState
    ) {
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
                    onActionClick = {
                        onTriggerEvent(
                            SettingsEvent.ResetSettings(
                                context.getString(R.string.default_settings_restored)
                            )
                        )
                    }
                )
            }
            item {
                SettingPanel(
                    modifier = Modifier,
                    panelName = stringResource(id = R.string.notifications),
                    mainName = stringResource(id = R.string.push_notifications),
                    checked = settings.pushNotifications,
                    onCheckedChange = { onTriggerEvent(SettingsEvent.UpdatePushNotifications(it)) }
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
                    Spacer(modifier = Modifier.size(paddingValues / 2))
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
                    Spacer(modifier = Modifier.size(paddingValues / 2))
                    Text(
                        text = stringResource(id = com.adwi.composables.R.string.screen_to_change),
                        modifier = Modifier.padding(horizontal = paddingValues)
                    )
                    CheckBoxRow(
                        name = stringResource(id = R.string.home_screen),
                        checked = settings.autoHome,
                        onCheckedChange = { onTriggerEvent(SettingsEvent.UpdateAutoHome(it)) })
                    CheckBoxRow(
                        name = stringResource(id = R.string.lock_screen),
                        checked = settings.autoLock,
                        onCheckedChange = { onTriggerEvent(SettingsEvent.UpdateAutoLock(it)) })
                    Spacer(modifier = Modifier.size(paddingValues))
                    Text(
                        text = stringResource(id = R.string.change_wallpaper_every),
                        modifier = Modifier.padding(horizontal = paddingValues)
                    )
                    Spacer(modifier = Modifier.size(paddingValues / 2))
                    DurationPicker(
                        modifier = Modifier,
                        days = days,
                        hours = hours,
                        minutes = minutes,
                        onDaysChange = { onTriggerEvent(SettingsEvent.SetDays(it)) },
                        onHourChange = { onTriggerEvent(SettingsEvent.SetHours(it)) },
                        onMinutesChange = { onTriggerEvent(SettingsEvent.SetMinutes(it)) }
                    )
                    Spacer(modifier = Modifier.size(paddingValues))
                    SaveButton(
                        modifier = Modifier,
                        onClick = {
                            onTriggerEvent(SettingsEvent.SaveSettings)
                            onTriggerEvent(
                                SettingsEvent.ShowMessageEvent(
                                    Event.ShowSnackBar(
                                        context.getString(com.adwi.composables.R.string.automation_saved)
                                    )
                                )
                            )
                        }
                    )
                }
            }
            item {
                SettingPanel(
                    modifier = Modifier,
                    panelName = stringResource(id = R.string.data_usage),
                    mainName = stringResource(id = R.string.activate_data_saver),
                    checked = settings.downloadOverWiFi,
                    onCheckedChange = { onTriggerEvent(SettingsEvent.UpdateDownloadOverWiFi(it)) }
                ) {
                    SwitchRow(
                        name = stringResource(id = R.string.download_wallpapers_only_over_wi_fi),
                        checked = settings.newWallpaperSet,
                        onCheckedChange = { onTriggerEvent(SettingsEvent.UpdateNewWallpaperSet(it)) }
                    )
                    SwitchRow(
                        name = stringResource(id = R.string.download_miniatures_only_in_tiny_resolution),
                        checked = settings.wallpaperRecommendations,
                        onCheckedChange = {
                            onTriggerEvent(
                                SettingsEvent.UpdateWallpaperRecommendations(
                                    it
                                )
                            )
                        }
                    )
                    SwitchRow(
                        name = stringResource(id = R.string.auto_change_only_on_wifi),
                        checked = settings.wallpaperRecommendations,
                        onCheckedChange = {
                            onTriggerEvent(
                                SettingsEvent.UpdateWallpaperRecommendations(
                                    it
                                )
                            )
                        }
                    )
                    Spacer(modifier = Modifier.size(paddingValues / 2))
                }
            }
            item {
                Column(Modifier.padding(top = paddingValues)) {
                    InfoRow(
                        onClick = {
                            onTriggerEvent(
                                SettingsEvent.ShowMessageEvent(
                                    Event.ShowSnackBar("Not implemented yet")
                                )
                            )
                        },
                        title = stringResource(id = R.string.about_us),
                        icon = Icons.Outlined.QuestionAnswer
                    )
                    InfoRow(
                        onClick = {
                            onTriggerEvent(
                                SettingsEvent.ShowMessageEvent(
                                    Event.ShowSnackBar("Not implemented yet")
                                )
                            )
                        },
                        title = stringResource(id = R.string.privacy_policy),
                        icon = Icons.Outlined.Security
                    )
                    InfoRow(
                        onClick = {
                            onTriggerEvent(
                                SettingsEvent.ShowMessageEvent(
                                    Event.ShowSnackBar("Not implemented yet")
                                )
                            )
                        },
                        title = stringResource(id = R.string.support),
                        icon = Icons.Outlined.Mail
                    )
                }
            }
        }
    }
}

@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@Composable
private fun SaveButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    var doNotShowRationale by rememberSaveable { mutableStateOf(false) }

    val readPermissionState = rememberPermissionState(
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    when {
        readPermissionState.hasPermission -> {
            Surface(
                onClick = onClick,
                modifier = modifier.fillMaxWidth(),
                color = MaterialTheme.colors.primaryVariant
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Save",
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(paddingValues)
                    )
                }
            }
        }
        readPermissionState.shouldShowRationale ||
                !readPermissionState.permissionRequested -> {
            if (doNotShowRationale) {
                Text("Feature not available")
            } else {
                Column {
                    Text("The Read External Storage is important for this app. Please grant the permission.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Button(onClick = { readPermissionState.launchPermissionRequest() }) {
                            Text("Request permission")
                        }
                        Spacer(Modifier.width(8.dp))
                        Button(onClick = { doNotShowRationale = true }) {
                            Text("Don't show rationale again")
                        }
                    }
                }
            }
        }
        else -> {
            Column {
                Text(
                    "Read External Storage permission denied. See this FAQ with information about why we " +
                            "need this permission. Please, grant us access on the Settings screen."
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
    val transition = updateTransition(targetState = checked, label = "Card")

    val elevation by transition.animateDp(label = "Card elevation") { state ->
        if (state) 10.dp else 2.dp
    }

    val backgroundColor by transition.animateColor(label = "Card background color") { state ->
        if (state) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
    }
    val textColor by transition.animateColor(label = "Card text color") { state ->
        if (state) MaterialTheme.colors.surface else MaterialTheme.colors.onSurface
    }
    val switchEnabledColor by transition.animateColor(label = "Card enabled switch color") { state ->
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
                checked = checked,
                onCheckedChange = {
                    onCheckedChange(it)
                },
                modifier = Modifier
                    .background(backgroundColor)
                    .padding(top = paddingValues / 2)
                    .padding(bottom = paddingValues / 2),
                textColor = textColor,
                switchEnabledColor = switchEnabledColor,
                switchDisabledColor = MaterialTheme.colors.primary
            )
            AnimatedVisibility(checked) {
                Column(
                    Modifier
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.size(paddingValues / 2))
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
    switchEnabledColor: Color = MaterialTheme.colors.primary,
    switchDisabledColor: Color = MaterialTheme.colors.secondary,
    backgroundColor: Color = MaterialTheme.colors.surface,
) {
    Surface(
        onClick = {
            onCheckedChange(!checked)
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
                checked = checked,
                onCheckedChange = {
                    onCheckedChange(it)
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = switchEnabledColor,
                    uncheckedThumbColor = switchDisabledColor
                )
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun HomeOrLockRow(
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
            text = stringResource(id = com.adwi.composables.R.string.screen_to_change) + ":",
            modifier = Modifier.padding(horizontal = paddingValues)
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
private fun CheckBoxRow(
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
                )
            )
            Text(
                text = name,
                modifier = Modifier.padding(start = paddingValues / 2)
            )
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
private fun DurationPicker(
    modifier: Modifier = Modifier,
    days: Int,
    hours: Int,
    minutes: Int,
    onDaysChange: (Int) -> Unit,
    onHourChange: (Int) -> Unit,
    onMinutesChange: (Int) -> Unit,
) {
    val dayMax = 31
    val hoursMax = 23
    val minutesMax = 59

    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = paddingValues)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Days",
                modifier = Modifier.align(Alignment.CenterStart),
                style = MaterialTheme.typography.subtitle1
            )
            TimeUnitItem(
                count = days,
                range = dayMax,
                onUpClick = { onDaysChange(days + 1) },
                onDownClick = { onDaysChange(days - 1) },
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Hours",
                modifier = Modifier.align(Alignment.CenterStart),
                style = MaterialTheme.typography.subtitle1
            )
            TimeUnitItem(
                count = hours,
                range = hoursMax,
                onUpClick = { onHourChange(hours + 1) },
                onDownClick = { onHourChange(hours - 1) },
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Minutes",
                modifier = Modifier.align(Alignment.CenterStart),
                style = MaterialTheme.typography.subtitle1
            )
            TimeUnitItem(
                count = minutes,
                range = minutesMax,
                onUpClick = { onMinutesChange(minutes + 1) },
                onDownClick = { onMinutesChange(minutes - 1) },
                modifier = Modifier.align(Alignment.Center)

            )
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
private fun TimeUnitItem(
    modifier: Modifier = Modifier,
    count: Int,
    range: Int,
    onUpClick: () -> Unit,
    onDownClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ) {

        IconButton(
            onClick = onDownClick,
            enabled = count > 0
        ) {
            Icon(
                imageVector = Icons.Outlined.RemoveCircleOutline,
                contentDescription = "Subtract"
            )
        }
        AnimatedCounter(count = count)
        IconButton(
            onClick = onUpClick,
            enabled = count < range
        ) {
            Icon(imageVector = Icons.Outlined.AddCircleOutline, contentDescription = "Add")
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun AnimatedCounter(
    modifier: Modifier = Modifier,
    count: Int
) {
    Surface(
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colors.primary
        ),
        shape = RoundedCornerShape(percent = 20),
        modifier = modifier.size(40.dp),
        color = MaterialTheme.colors.primary,
        elevation = 4.dp
    ) {
        Row(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedContent(
                targetState = count,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInVertically(initialOffsetY = { height -> -height }) + fadeIn() with
                                slideOutVertically(targetOffsetY = { height -> height }) + fadeOut()
                    } else {
                        slideInVertically(initialOffsetY = { height -> height }) + fadeIn() with
                                slideOutVertically(targetOffsetY = { height -> -height }) + fadeOut()
                    }.using(
                        SizeTransform(clip = false)
                    )
                }
            ) { targetCount ->
                Text(
                    text = "$targetCount",
                    modifier = Modifier.padding(4.dp),
                    color = MaterialTheme.colors.primaryVariant
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun InfoRow(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    title: String,
    icon: ImageVector
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.background
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                shape = RoundedCornerShape(30),
                elevation = 6.dp,
                modifier = Modifier.padding(paddingValues / 2),
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Box() {
                    Icon(
                        imageVector = icon,
                        tint = MaterialTheme.colors.primaryVariant,
                        contentDescription = title,
                        modifier = Modifier
                            .padding(paddingValues / 2)
                            .align(Alignment.Center)
                    )
                }

            }
            Text(
                text = title,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = paddingValues / 2),
                color = MaterialTheme.colors.primary
            )
        }
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
private fun AnimatedCounterPreview() {
    PexWallpapersTheme {
        AnimatedCounter(count = 5)
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
private fun NumberSetterItemPreview() {
    PexWallpapersTheme {
        DurationPicker(
            days = 3,
            hours = 2,
            minutes = 7,
            onMinutesChange = {},
            onHourChange = {},
            onDaysChange = {}
        )
    }
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
        HomeOrLockRow(
            homeChecked = true,
            onHomeCheckedChange = {},
            lockChecked = false,
            onLockCheckedChange = {})
    }
}