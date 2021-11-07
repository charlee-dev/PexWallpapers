package com.adwi.data.cache.daily

import com.adwi.data.cache.DailyEntity
import com.adwi.data.network.model.WallpaperDto

fun wallpaperToDailyWallpaper(wallpaper: WallpaperDto) =
    DailyEntity(wallpaper.id.toLong())