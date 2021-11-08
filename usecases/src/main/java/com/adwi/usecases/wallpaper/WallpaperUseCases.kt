package com.adwi.usecases.wallpaper

import com.adwi.core.util.Logger
import com.adwi.data.cache.wallpaper.WallpaperCache
import com.adwi.data.network.service.PexService
import com.adwi.usecases.wallpaper.usecases.GetCuratedWallpapers
import com.squareup.sqldelight.db.SqlDriver


data class WallpaperUseCases(
    val getCuratedWallpapers: GetCuratedWallpapers,
    // TODO(add other useCases)
) {
    companion object Factory {
        fun build(sqlDriver: SqlDriver): WallpaperUseCases {
            val service = PexService.build()
            val cache = WallpaperCache.build(sqlDriver)
            val logger = Logger()
            return WallpaperUseCases(
                getCuratedWallpapers = GetCuratedWallpapers(
                    service = service,
                    logger = logger,
                    cache = cache
                ),
            )
        }

        val schema: SqlDriver.Schema = WallpaperCache.schema

        val dbName: String = WallpaperCache.dbName
    }
}