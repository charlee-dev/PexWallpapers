package com.adwi.repository.util

import com.adwi.pexwallpapers.util.Constants.DEFAULT_CATEGORY
import com.adwi.pexwallpapers.util.Constants.REFRESH_DATA_EVERY
import com.adwi.pexwallpapers.data.wallpapers.database.domain.WallpaperEntity
import com.adwi.pexwallpapers.data.wallpapers.network.domain.WallpaperDto
import com.adwi.pexwallpapers.data.wallpapers.network.domain.toDomain
import com.adwi.domain.ColorCategory
import com.adwi.domain.Wallpaper
import java.util.concurrent.TimeUnit

fun List<com.adwi.pexwallpapers.data.wallpapers.network.domain.WallpaperDto>.keepFavorites(
    categoryName: String = DEFAULT_CATEGORY,
    favoritesList: List<com.adwi.pexwallpapers.data.wallpapers.database.domain.WallpaperEntity>
) =
    this.map { wallpaperDto ->
        val isFavorite = favoritesList.any { favoriteWallpaper ->
            favoriteWallpaper.id == wallpaperDto.id
        }
        wallpaperDto.toDomain(categoryName = categoryName, isFavorite = isFavorite)
    }

fun List<Wallpaper>.shouldFetch(): Boolean {
    val sortedWallpapers = this.sortedBy { wallpaper ->
        wallpaper.updatedAt
    }
    val oldestTimestamp = sortedWallpapers.firstOrNull()?.updatedAt
    return oldestTimestamp == null ||
            oldestTimestamp < System.currentTimeMillis() -
            TimeUnit.DAYS.toMillis(REFRESH_DATA_EVERY)
}

fun List<ColorCategory>.shouldFetchColors(): Boolean {
    val sortedWallpapers = this.sortedBy { color ->
        color.timeStamp
    }
    val oldestTimestamp = sortedWallpapers.firstOrNull()?.timeStamp
    return oldestTimestamp == null ||
            oldestTimestamp < System.currentTimeMillis() -
            TimeUnit.DAYS.toMillis(REFRESH_DATA_EVERY)
}