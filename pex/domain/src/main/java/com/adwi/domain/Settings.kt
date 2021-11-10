package com.adwi.domain

data class Settings(
    val id: Long = 0L,
    val lastQuery: String = "",
    val newWallpaperSet: Boolean = true,
    val wallpaperRecommendations: Boolean = true,
    val autoChangeWallpaper: Boolean = false,
    val selectedButton: Int = 0,
    val sliderValue: Float = 5f,
    val downloadOverWiFi: Boolean = false,
    val autoHome: Boolean = true,
    val autoLock: Boolean = false
)
