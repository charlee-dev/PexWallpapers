package com.adwi.interactors.settings.usecases

import com.adwi.datasource.local.WallpaperDatabase
import com.adwi.datasource.local.domain.toEntity
import com.adwi.domain.Settings
import javax.inject.Inject

class GetSettings @Inject constructor(
    database: WallpaperDatabase
) {
    val dao = database.settingsDao()

    fun getSettings() = dao.getSettings()

    suspend fun insertSettings(settings: Settings) {
        dao.insertSettings(settings.toEntity())
    }

    suspend fun updateLastQuery(query: String) {
        dao.updateLastQuery(query)
    }

    suspend fun updateNewWallpaperSet(enabled: Boolean) =
        dao.updateNewWallpaperSet(enabled)

    suspend fun updateWallpaperRecommendations(enabled: Boolean) =
        dao.updateWallpaperRecommendations(enabled)

    suspend fun updateAutoChangeWallpaper(enabled: Boolean) =
        dao.updateAutoChangeWallpaper(enabled)

    suspend fun updateDownloadOverWiFi(enabled: Boolean) =
        dao.updateDownloadOverWiFi(enabled)

    suspend fun updateChangePeriodType(radioButton: Int) =
        dao.updateChangePeriodType(radioButton)

    suspend fun updateChangePeriodValue(periodValue: Float) =
        dao.updateChangePeriodValue(periodValue)

    suspend fun updateAutoHome(enabled: Boolean) {
        dao.updateAutoHome(enabled)
    }

    suspend fun updateAutoLock(enabled: Boolean) {
        dao.updateAutoLock(enabled)
    }

    suspend fun resetAllSettings() = dao.insertSettings(Settings.default.toEntity())
}