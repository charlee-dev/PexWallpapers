package com.adwi.pexwallpapers.data

import com.adwi.pexwallpapers.data.database.wallpapers.domain.WallpaperEntity
import com.adwi.pexwallpapers.data.network.domain.WallpaperDto
import com.adwi.pexwallpapers.data.network.domain.toDomain
import com.adwi.pexwallpapers.domain.model.ColorCategory
import com.adwi.pexwallpapers.domain.model.Wallpaper
import com.adwi.pexwallpapers.presentation.util.Constants.DEFAULT_CATEGORY
import com.adwi.pexwallpapers.presentation.util.Constants.REFRESH_DATA_EVERY
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