package com.adwi.datasource.local.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adwi.core.util.Constants.DEFAULT_QUERY
import com.adwi.domain.Settings

@Entity(tableName = "settings_table")
data class SettingsEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val lastQuery: String = DEFAULT_QUERY,
    val newWallpaperSet: Boolean = true,
    val wallpaperRecommendations: Boolean = true,
    val autoChangeWallpaper: Boolean = false,
    val selectedButton: Int = 0,
    val sliderValue: Float = 5f,
    val downloadOverWiFi: Boolean = false,
    val autoHome: Boolean = true,
    val autoLock: Boolean = false
)

fun Settings.toEntity() =
    SettingsEntity(
        id,
        lastQuery,
        newWallpaperSet,
        wallpaperRecommendations,
        autoChangeWallpaper,
        selectedButton,
        sliderValue,
        downloadOverWiFi
    )

fun SettingsEntity.toDomain() =
    Settings(
        id,
        lastQuery,
        newWallpaperSet,
        wallpaperRecommendations,
        autoChangeWallpaper,
        selectedButton,
        sliderValue,
        downloadOverWiFi
    )