package com.adwi.pexwallpapers.data.database.settings.model

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
    val duration: Int = 1,

    val activateDataSaver: Boolean = false,
    val downloadWallpapersOverWiFi: Boolean = false,
    val lowResMiniatures: Boolean = false,
    val autoChangeOverWiFi: Boolean = false
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
            duration = 1,

            activateDataSaver = false,
            downloadWallpapersOverWiFi = false,
            lowResMiniatures = false,
            autoChangeOverWiFi = false
        )
    }
}