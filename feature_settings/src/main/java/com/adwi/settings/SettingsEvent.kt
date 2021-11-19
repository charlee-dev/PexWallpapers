package com.adwi.settings

sealed class SettingsEvent {

    object GetSettings : SettingsEvent()
    object GetFavorites : SettingsEvent()

    data class UpdateNewWallpaperSet(val checked: Boolean) : SettingsEvent()
    data class UpdateWallpaperRecommendations(val checked: Boolean) : SettingsEvent()
    data class UpdateAutoChangeWallpaper(val checked: Boolean) : SettingsEvent()

    data class UpdateChangePeriodType(val button: Int) : SettingsEvent()
    data class UpdateChangePeriodValue(val value: Float) : SettingsEvent()
    data class UpdateAutoHome(val checked: Boolean) : SettingsEvent()
    data class UpdateAutoLock(val checked: Boolean) : SettingsEvent()
    data class UpdateDownloadOverWiFi(val checked: Boolean) : SettingsEvent()

    object ResetSettings : SettingsEvent()
    object ContactSupport : SettingsEvent()
}
