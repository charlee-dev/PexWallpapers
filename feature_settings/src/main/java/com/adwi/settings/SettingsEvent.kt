package com.adwi.settings

import com.adwi.domain.Duration

sealed class SettingsEvent {

    object GetSettings : SettingsEvent()
    object GetFavorites : SettingsEvent()

    data class UpdatePushNotifications(val checked: Boolean) : SettingsEvent()
    data class UpdateNewWallpaperSet(val checked: Boolean) : SettingsEvent()
    data class UpdateWallpaperRecommendations(val checked: Boolean) : SettingsEvent()

    data class UpdateAutoChangeWallpaper(val checked: Boolean) : SettingsEvent()
    data class UpdateChangeDurationSelected(val durationSelected: Duration) : SettingsEvent()
    data class UpdateChangeDurationValue(val durationValue: Float) : SettingsEvent()
    data class UpdateAutoHome(val checked: Boolean) : SettingsEvent()
    data class UpdateAutoLock(val checked: Boolean) : SettingsEvent()
    data class UpdateDownloadOverWiFi(val checked: Boolean) : SettingsEvent()

    data class ResetSettings(val message: String) : SettingsEvent()
    object ContactSupport : SettingsEvent()

    data class SetDays(val value: Int) : SettingsEvent()
    data class SetHours(val value: Int) : SettingsEvent()
    data class SetMinutes(val value: Int) : SettingsEvent()

    object SaveSettings : SettingsEvent()

    data class SetSnackBarMessage(val message: String) : SettingsEvent()
}
