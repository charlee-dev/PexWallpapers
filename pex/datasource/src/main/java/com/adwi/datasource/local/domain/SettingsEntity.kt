package com.adwi.datasource.local.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adwi.core.util.Constants.DEFAULT_QUERY

@Entity(tableName = "settings_table")
data class SettingsEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 1,
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