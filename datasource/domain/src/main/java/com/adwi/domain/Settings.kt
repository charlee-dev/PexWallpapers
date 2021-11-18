package com.adwi.domain

data class Settings(
    val id: Int = 0,
    val lastQuery: String = "",
    val newWallpaperSet: Boolean = true,
    val wallpaperRecommendations: Boolean = true,
    val autoChangeWallpaper: Boolean = false,
    val selectedButton: Int = 0,
    val sliderValue: Float = 5f,
    val downloadOverWiFi: Boolean = false,
    val autoHome: Boolean = true,
    val autoLock: Boolean = false
) {
    companion object {
        val default = Settings(
            id = 0,
            lastQuery = "Flowers",
            newWallpaperSet = true,
            wallpaperRecommendations = true,
            autoChangeWallpaper = false,
            selectedButton = 0,
            sliderValue = 5f,
            downloadOverWiFi = false,
            autoHome = true,
            autoLock = false
        )
    }
}
