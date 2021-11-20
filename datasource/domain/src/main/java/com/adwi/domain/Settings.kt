package com.adwi.domain

data class Settings(
    val id: Int = 0,
    val lastQuery: String = DEFAULT_QUERY,
    val pushNotifications: Boolean = true,
    val newWallpaperSet: Boolean = true,
    val wallpaperRecommendations: Boolean = true,
    val autoChangeWallpaper: Boolean = false,
    val durationSelected: Duration = Duration.MINUTE,
    val durationValue: Float = 5f,
    val downloadOverWiFi: Boolean = false,
    val autoHome: Boolean = true,
    val autoLock: Boolean = false
) {
    companion object {
        val default = Settings(
            id = 0,
            lastQuery = DEFAULT_QUERY,
            pushNotifications = true,
            newWallpaperSet = true,
            wallpaperRecommendations = true,
            autoChangeWallpaper = false,
            durationSelected = Duration.MINUTE,
            durationValue = 5f,
            downloadOverWiFi = false,
            autoHome = true,
            autoLock = false
        )
    }
}
enum class Duration(value: Int) {
    MINUTE(value = 0),
    HOUR(value = 1),
    DAY(value = 2),
}

const val DEFAULT_QUERY = "Flowers"