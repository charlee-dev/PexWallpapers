package com.adwi.interactors.common

import com.adwi.core.util.Constants.DEFAULT_CATEGORY
import com.adwi.core.util.Constants.REFRESH_DATA_EVERY
import com.adwi.datasource.local.domain.ColorCategoryEntity
import com.adwi.datasource.local.domain.WallpaperEntity
import com.adwi.datasource.network.domain.WallpaperDto
import com.adwi.datasource.network.domain.toDomain
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

fun List<WallpaperEntity>.shouldFetch(): Boolean {
    val sortedWallpapers = this.sortedBy { wallpaper ->
        wallpaper.updatedAt
    }
    val oldestTimestamp = sortedWallpapers.firstOrNull()?.updatedAt
    return oldestTimestamp == null ||
            oldestTimestamp < System.currentTimeMillis() -
            TimeUnit.DAYS.toMillis(REFRESH_DATA_EVERY)
}

fun List<ColorCategoryEntity>.shouldFetchColors(): Boolean {
    val sortedWallpapers = this.sortedBy { color ->
        color.timeStamp
    }
    val oldestTimestamp = sortedWallpapers.firstOrNull()?.timeStamp
    return oldestTimestamp == null ||
            oldestTimestamp < System.currentTimeMillis() -
            TimeUnit.DAYS.toMillis(REFRESH_DATA_EVERY)
}