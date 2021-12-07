package com.adwi.feature_settings.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings_table")
data class Settings(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,

    val pushNotifications: Boolean = true,
    val newWallpaperSet: Boolean = true,
    val wallpaperRecommendations: Boolean = true,

    val autoChangeWallpaper: Boolean = false,
    val autoHome: Boolean = true,
    val autoLock: Boolean = false,
    val minutes: Int = 15,
    val hours: Int = 3,
    val days: Int = 0,

    val activateDataSaver: Boolean = false,
    val downloadWallpapersOverWiFi: Boolean = false,
    val lowResMiniatures: Boolean = false,
    val autoChangeOverWiFi: Boolean = false,

    val showShadows: Boolean = true,
    val showParallax: Boolean = true
) {
    companion object {
        val default = Settings(
            id = 0,

            pushNotifications = true,
            newWallpaperSet = true,
            wallpaperRecommendations = true,

            autoChangeWallpaper = false,
            autoHome = true,
            autoLock = false,
            minutes = 15,
            hours = 3,
            days = 0,

            activateDataSaver = false,
            downloadWallpapersOverWiFi = false,
            lowResMiniatures = false,
            autoChangeOverWiFi = false,
            showShadows = true,
            showParallax = true
        )
    }
}