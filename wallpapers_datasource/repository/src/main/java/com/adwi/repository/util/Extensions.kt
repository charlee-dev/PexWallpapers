package com.adwi.repository.util

import com.adwi.data.Constants.DEFAULT_CATEGORY
import com.adwi.data.Constants.REFRESH_DATA_EVERY
import com.adwi.data.database.domain.WallpaperEntity
import com.adwi.data.network.domain.WallpaperDto
import com.adwi.data.network.domain.toDomain
import com.adwi.pexwallpapers.domain.model.ColorCategory
import com.adwi.pexwallpapers.domain.model.Wallpaper
import java.util.concurrent.TimeUnit

fun List<WallpaperDto>.keepFavorites(
    categoryName: String = DEFAULT_CATEGORY,
    favoritesList: List<WallpaperEntity>
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