package com.adwi.feature_settings.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.*
import com.adwi.components.theme.Dimensions.BottomBar.BottomNavHeight
import com.adwi.components.theme.paddingValues
import com.adwi.feature_settings.R
import com.adwi.feature_settings.presentation.components.CheckBoxRow
import com.adwi.feature_settings.presentation.components.DurationPicker
import com.adwi.feature_settings.presentation.components.InfoRow
import com.adwi.feature_settings.presentation.components.SaveButton
import com.google.accompanist.permissions.ExperimentalPermissionsApi
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
    onContactSupportClick: () -> Unit,
    onSaveAutomationClick: () -> Unit,
    cancelWorks: () -> Unit
) {
    val settings by viewModel.settings.collectAsState()

    val context = LocalContext.current

    PexScaffold(
        viewModel = viewModel
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
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
                    },
                    showShadows = settings.showShadows
                )
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = paddingValues)
                ) {
                    CategoryTitle(
                        name = stringResource(id = R.string.notifications),
                        modifier = Modifier.padding(bottom = paddingValues)
                    )
                    PexExpandableCard(
                        modifier = Modifier,
                        headerText = stringResource(id = R.string.push_notifications),
                        showShadows = settings.showShadows
                    ) {
                        SwitchRow(
                            name = stringResource(id = R.string.new_wallpaper_set),
                            checked = settings.newWallpaperSet,
                            onCheckedChange = { viewModel.updateNewWallpaperSet(it) }
                        )
                        OptionTip(text = stringResource(R.string.new_wallpaper_set_description))
                        SwitchRow(
                            name = stringResource(id = R.string.wallpaper_recommendations),
                            checked = settings.wallpaperRecommendations,
                            onCheckedChange = { viewModel.updateWallpaperRecommendations(it) }
                        )
                        Spacer(modifier = Modifier.size(paddingValues / 2))
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = paddingValues)
                ) {
                    CategoryTitle(
                        name = stringResource(id = R.string.automation),
                        modifier = Modifier.padding(top = paddingValues / 2, bottom = paddingValues)
                    )
                    PexExpandableCard(
                        modifier = Modifier,
                        headerText = stringResource(id = R.string.auto_change_wallpaper),
                        showShadows = settings.showShadows
                    ) {
                        Spacer(modifier = Modifier.size(paddingValues / 2))
                        Text(
                            text = stringResource(id = R.string.screen_to_change) + ":",
                            modifier = Modifier.padding(horizontal = paddingValues)
                        )
                        OptionTip(text = "Minimum one of screens need to be chosen")
                        AnimatedVisibility(
                            visible = !settings.autoHome && !settings.autoLock,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            OptionTip(
                                text = "Choose minimum one screen to change wallpaper",
                                color = Color.Red
                            )
                        }
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
                            days = settings.days,
                            hours = settings.hours,
                            minutes = settings.minutes,
                            onDaysChange = { viewModel.updateDays(it) },
                            onHourChange = { viewModel.updateHours(it) },
                            onMinutesChange = { viewModel.updateMinutes(it) },
                            showShadows = viewModel.showShadows
                        )
                        Spacer(modifier = Modifier.size(paddingValues / 2))
                        OptionTip(text = stringResource(R.string.change_wallpaper_every_description))
                        Spacer(modifier = Modifier.size(paddingValues / 2))
                        SaveButton(
                            enabled = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            onClick = { onSaveAutomationClick() },
                            showShadows = settings.showShadows
                        )
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = paddingValues)
                ) {
                    CategoryTitle(
                        name = stringResource(id = R.string.data_usage),
                        modifier = Modifier.padding(top = paddingValues / 2, bottom = paddingValues)
                    )
                    PexExpandableCard(
                        modifier = Modifier,
                        headerText = stringResource(id = R.string.activate_data_saver),
                        showShadows = settings.showShadows
                    ) {
                        SwitchRow(
                            name = stringResource(id = R.string.download_wallpapers_only_over_wi_fi),
                            checked = settings.downloadWallpapersOverWiFi,
                            onCheckedChange = { viewModel.updateDownloadWallpapersOverWiFi(it) }
                        )
                        OptionTip(text = stringResource(R.string.download_over_wifi_description))
                        SwitchRow(
                            name = stringResource(id = R.string.download_miniatures_low_quality),
                            checked = settings.lowResMiniatures,
                            onCheckedChange = { viewModel.updateDownloadMiniaturesLowQuality(it) }
                        )
                        OptionTip(text = stringResource(R.string.low_res_description))
                        SwitchRow(
                            name = stringResource(id = R.string.auto_change_only_on_wifi),
                            checked = settings.autoChangeOverWiFi,
                            onCheckedChange = { viewModel.updateAutoChangeOverWiFi(it) }
                        )
                        Spacer(modifier = Modifier.size(paddingValues / 2))
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = paddingValues)
                ) {
                    CategoryTitle(
                        name = stringResource(id = R.string.performance),
                        modifier = Modifier.padding(top = paddingValues / 2, bottom = paddingValues)
                    )
                    PexExpandableCard(
                        modifier = Modifier,
                        headerText = stringResource(id = R.string.gain_on_performance),
                        showShadows = settings.showShadows
                    ) {
                        SwitchRow(
                            name = stringResource(id = R.string.show_shadows),
                            checked = settings.showShadows,
                            onCheckedChange = { viewModel.updateDisableShadows(it) }
                        )
                        OptionTip(text = stringResource(R.string.show_shadows_tip))
                        SwitchRow(
                            name = stringResource(id = R.string.show_parallax_effect),
                            checked = settings.showParallax,
                            onCheckedChange = { viewModel.updateDisableParallax(it) }
                        )
                        OptionTip(text = stringResource(R.string.show_parallax_effect_tip))
                        Spacer(modifier = Modifier.size(paddingValues / 2))
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .padding(top = paddingValues)
                        .padding(horizontal = paddingValues)
                ) {
                    InfoRow(
                        onClick = onAboutUsClick,
                        title = stringResource(id = R.string.about_us),
                        icon = Icons.Outlined.QuestionAnswer,
                        showShadows = viewModel.showShadows
                    )
                    InfoRow(
                        onClick = onPrivacyPolicyClick,
                        title = stringResource(id = R.string.privacy_policy),
                        icon = Icons.Outlined.Security,
                        showShadows = viewModel.showShadows
                    )
                    InfoRow(
                        onClick = onContactSupportClick,
                        title = stringResource(id = R.string.support),
                        icon = Icons.Outlined.Mail,
                        showShadows = viewModel.showShadows
                    )
                }
            }
        }
    }
}

@Composable
private fun OptionTip(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colors.onBackground
) {
    Text(
        text = text,
        style = MaterialTheme.typography.caption,
        color = color,
        modifier = modifier
            .padding(horizontal = paddingValues)
            .padding(end = paddingValues, bottom = paddingValues / 2)
    )
}