package com.adwi.pexwallpapers.presentation.screens.settings

import androidx.compose.animation.*
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.pexwallpapers.R
import com.adwi.pexwallpapers.domain.state.Result
import com.adwi.pexwallpapers.presentation.components.*
import com.adwi.pexwallpapers.presentation.theme.Dimensions.BottomBar.BottomNavHeight
import com.adwi.pexwallpapers.presentation.theme.PexWallpapersTheme
import com.adwi.pexwallpapers.presentation.theme.paddingValues
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
    onAboutUsClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onContactSupportClick: () -> Unit
) {
    val settings by viewModel.settings.collectAsState()
    val days by viewModel.days.collectAsState()
    val hours by viewModel.hours.collectAsState()
    val minutes by viewModel.minutes.collectAsState()

    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

//    val subject =
//        "${context.getString(R.string.support_title)} 12345678" // TODO(implement support messaging)

//    val chooserMessage = context.getString(R.string.support_chooser_message)
//
//    val intent = Intent(Intent.ACTION_SENDTO).apply {
//        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        putExtra(Intent.EXTRA_SUBJECT, subject)
//    }

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
                        viewModel.resetSettings()
                        viewModel.setSnackBar(context.getString(R.string.default_settings_restored))
                    }
                )
            }
            item {
                SettingPanel(
                    modifier = Modifier,
                    panelName = stringResource(id = R.string.notifications),
                    mainName = stringResource(id = R.string.push_notifications),
                    checked = settings.pushNotifications,
                    onCheckedChange = { viewModel.updatePushNotifications(it) }
                ) {
                    SwitchRow(
                        name = stringResource(id = R.string.new_wallpaper_set),
                        checked = settings.newWallpaperSet,
                        onCheckedChange = { viewModel.updateNewWallpaperSet(it) }
                    )
                    SwitchRow(
                        name = stringResource(id = R.string.wallpaper_recommendations),
                        checked = settings.wallpaperRecommendations,
                        onCheckedChange = { viewModel.updateWallpaperRecommendations(it) }
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
                    onCheckedChange = { viewModel.updateAutoChangeWallpaper(it) }
                ) {
                    Spacer(modifier = Modifier.size(paddingValues / 2))
                    Text(
                        text = stringResource(id = R.string.screen_to_change),
                        modifier = Modifier.padding(horizontal = paddingValues)
                    )
                    CheckBoxRow(
                        name = stringResource(id = R.string.home_screen),
                        checked = settings.autoHome,
                        onCheckedChange = { viewModel.updateAutoHome(it) }
                    )
                    CheckBoxRow(
                        name = stringResource(id = R.string.lock_screen),
                        checked = settings.autoLock,
                        onCheckedChange = { viewModel.updateAutoLock(it) }
                    )
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
                        onDaysChange = { viewModel.updateDuration(d = it) },
                        onHourChange = { viewModel.updateDuration(h = it) },
                        onMinutesChange = { viewModel.updateDuration(m = it) }
                    )
                    Spacer(modifier = Modifier.size(paddingValues))
                    SaveButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        onClick = { viewModel.saveAutomation(context) },
                    )
                }
            }
            item {
                SettingPanel(
                    modifier = Modifier,
                    panelName = stringResource(id = R.string.data_usage),
                    mainName = stringResource(id = R.string.activate_data_saver),
                    checked = settings.activateDataSaver,
                    onCheckedChange = { viewModel.updateActivateDataSaver(it) }
                ) {
                    SwitchRow(
                        name = stringResource(id = R.string.download_wallpapers_only_over_wi_fi),
                        checked = settings.downloadWallpapersOverWiFi,
                        onCheckedChange = { viewModel.updateDownloadWallpapersOverWiFi(it) }
                    )
                    SwitchRow(
                        name = stringResource(id = R.string.download_miniatures_low_quality),
                        checked = settings.lowResMiniatures,
                        onCheckedChange = { viewModel.updateDownloadMiniaturesLowQuality(it) }
                    )
                    SwitchRow(
                        name = stringResource(id = R.string.auto_change_only_on_wifi),
                        checked = settings.autoChangeOverWiFi,
                        onCheckedChange = { viewModel.updateAutoChangeOverWiFi(it) }
                    )
                    Spacer(modifier = Modifier.size(paddingValues / 2))
                }
            }
            item {
                Column(Modifier.padding(top = paddingValues)) {
                    InfoRow(
                        onClick = onAboutUsClick,
                        title = stringResource(id = R.string.about_us),
                        icon = Icons.Outlined.QuestionAnswer
                    )
                    InfoRow(
                        onClick = onPrivacyPolicyClick,
                        title = stringResource(id = R.string.privacy_policy),
                        icon = Icons.Outlined.Security
                    )
                    InfoRow(
                        onClick = onContactSupportClick,
                        title = stringResource(id = R.string.support),
                        icon = Icons.Outlined.Mail
                    )
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@Composable
private fun SaveButton(
    modifier: Modifier = Modifier,
    state: Result = Result.Idle,
    onClick: () -> Unit
) {
    var doNotShowRationale by rememberSaveable { mutableStateOf(false) }

    val readPermissionState = rememberPermissionState(
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    when {
        readPermissionState.hasPermission -> {
            PexButton(
                state = state,
                onClick = onClick,
                shape = RectangleShape,
                text = stringResource(id = R.string.save),
                successText = stringResource(id = R.string.automation_saved),
                modifier = modifier
            )

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
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val transition = updateTransition(targetState = checked, label = "Card")

    val backgroundColor by transition.animateColor(label = "Card background color") { state ->
        if (state) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
    }
    val textColor by transition.animateColor(label = "Card text color") { state ->
        if (state) MaterialTheme.colors.secondary else MaterialTheme.colors.onBackground
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
//        elevation = elevation,
        shape = shape,
        modifier = Modifier.neumorphicShadow(pressed = isPressed)
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
    textColor: Color = MaterialTheme.colors.onBackground,
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
            text = stringResource(id = R.string.screen_to_change) + ":",
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
                ),
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
        modifier = modifier
            .size(40.dp)
            .neumorphicShadow(
                cornerRadius = 10.dp,
                offset = (-5).dp
            ),
        color = MaterialTheme.colors.primary,
//        elevation = 4.dp
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
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

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
//                elevation = 6.dp,
                modifier = Modifier
                    .padding(paddingValues / 2)
                    .neumorphicShadow(
                        pressed = isPressed,
                        cornerRadius = 10.dp,
                        offset = (-5).dp
                    ),
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Box {
                    Icon(
                        imageVector = icon,
                        tint = MaterialTheme.colors.secondary,
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