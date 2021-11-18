package com.adwi.interactors.settings

import com.adwi.domain.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getSettings(): Flow<Settings>
    suspend fun insertSettings(settings: Settings)
    suspend fun updateLastQuery(query: String)
    suspend fun updateNewWallpaperSet(enabled: Boolean)
    suspend fun updateWallpaperRecommendations(enabled: Boolean)
    suspend fun updateAutoChangeWallpaper(enabled: Boolean)
    suspend fun updateDownloadOverWiFi(enabled: Boolean)
    suspend fun updateChangePeriodType(radioButton: Int)
    suspend fun updateChangePeriodValue(periodValue: Float)
    suspend fun updateAutoHome(enabled: Boolean)
    suspend fun updateAutoLock(enabled: Boolean)
    suspend fun resetAllSettings()
}
