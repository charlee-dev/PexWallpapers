package com.adwi.data.util

import com.adwi.data.util.Constants.DEFAULT_CATEGORY
import com.adwi.data.util.Constants.REFRESH_DATA_EVERY
import com.adwi.data.database.domain.WallpaperEntity
import com.adwi.data.network.domain.WallpaperDto
import com.adwi.data.network.domain.toDomain
import com.adwi.pexwallpapers.domain.model.Wallpaper
import retrofit2.HttpException
import java.io.IOException
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

fun Throwable.throwIfException() {
    if (this !is HttpException && this !is IOException) {
        throw this
    }
}