package com.adwi.data.cache.settings

import com.adwi.data.cache.SettingsEntity
import com.adwi.data.cache.WallpaperDb
import com.adwi.data.cache.WallpaperDbQueries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SettingsCacheImpl(
    private val wallpaperDb: WallpaperDb
) : SettingsCache {

    private var queries: WallpaperDbQueries = wallpaperDb.wallpaperDbQueries

    override suspend fun insertSettings(setting: SettingsEntity) = setting.run {
        queries.insertSetting(
            id,
            lastQuery,
            newWallpaperSet,
            wallpaperRecommendations,
            autoChangeWallpaper,
            selectedButton,
            sliderValue,
            downloadOverWiFi,
            autoHome,
            autoLock
        )
    }

    override fun getSettings(): Flow<SettingsEntity> = flow {
        queries.getSetting().executeAsList()
    }

    override suspend fun updateLastQuery(query: String) {
        return queries.updateLastQuery(query)
    }

    override suspend fun updateNewWallpaperSet(enabled: Boolean) {
        return queries.updateNewWallpaperSet(enabled)
    }

    override suspend fun updateWallpaperRecommendations(enabled: Boolean) {
        return queries.updateWallpaperRecommendations(enabled)
    }

    override suspend fun updateAutoChangeWallpaper(enabled: Boolean) {
        return queries.updateAutoChangeWallpaper(enabled)
    }

    override suspend fun updateDownloadOverWiFi(enabled: Boolean) {
        return queries.updateDownloadOverWiFi(enabled)
    }

    override suspend fun updateChangePeriodType(radioButton: Int) {
        return queries.updateChangePeriodType(radioButton)
    }

    override suspend fun updateChangePeriodValue(periodValue: Float) {
        return queries.updateChangePeriodValue(periodValue)
    }

    override suspend fun updateAutoHome(enabled: Boolean) {
        return queries.updateAutoHome(enabled)
    }

    override suspend fun updateAutoLock(enabled: Boolean) {
        return queries.updateAutoLock(enabled)
    }
}