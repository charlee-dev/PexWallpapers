package com.adwi.usecases.wallpaper

import com.adwi.core.util.Logger
import com.adwi.data.network.service.PexService
import com.adwi.usecases.wallpaper.usecases.GetCuratedWallpapers


data class WallpaperUseCases(
    val getCuratedWallpapers: GetCuratedWallpapers,
    // TODO(add other useCases)
) {
    companion object Factory {
        fun build(): WallpaperUseCases {
            val service = PexService.build()
            val logger = Logger()
            return WallpaperUseCases(
                getCuratedWallpapers = GetCuratedWallpapers(
                    service = service,
                    logger = logger
                ),
            )
        }
    }
}