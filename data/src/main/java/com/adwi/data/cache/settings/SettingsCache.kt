package com.adwi.data.cache.settings

import com.adwi.data.cache.SettingsEntity
import kotlinx.coroutines.flow.Flow

interface SettingsCache {

    suspend fun insertSettings(setting: SettingsEntity)

    fun getSettings(): Flow<SettingsEntity>

    suspend fun updateLastQuery(query: String)

    suspend fun updateNewWallpaperSet(enabled: Boolean)

    suspend fun updateWallpaperRecommendations(enabled: Boolean)

    suspend fun updateAutoChangeWallpaper(enabled: Boolean)

    suspend fun updateDownloadOverWiFi(enabled: Boolean)

    suspend fun updateChangePeriodType(radioButton: Int)

    suspend fun updateChangePeriodValue(periodValue: Float)

    suspend fun updateAutoHome(enabled: Boolean)

    suspend fun updateAutoLock(enabled: Boolean)
}