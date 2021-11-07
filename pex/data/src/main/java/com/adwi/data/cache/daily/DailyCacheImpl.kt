package com.adwi.data.cache.daily

import com.adwi.data.cache.DailyEntity
import com.adwi.data.cache.WallpaperDb
import com.adwi.data.cache.WallpaperDbQueries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DailyCacheImpl(
    private val wallpaperDb: WallpaperDb
) : DailyCache {

    private var queries: WallpaperDbQueries = wallpaperDb.wallpaperDbQueries

    override suspend fun insertDaily(wallpaper: DailyEntity) {
        return wallpaper.run {
            queries.insertDaily(wallpaperId)
        }
    }

    override suspend fun getAllDaily(): Flow<List<DailyEntity>> = flow {
        queries.getAllDaily().executeAsList()
    }

    override suspend fun deleteAllDaily() {
        return queries.deleteAllDaily()
    }
}