package com.adwi.data.cache.model

import com.adwi.data.cache.WallpaperEntity
import com.adwi.domain.Wallpaper

fun Wallpaper.toEntity() =
    WallpaperEntity(
        id = id,
        height = height,
        url = url,
        photographer = photographer,
        categoryName = categoryName,
        isFavorite = isFavorite,
        updatedAt = updatedAt,
        imageUrlPortrait = imageUrlPortrait,
        imageUrlLandscape = imageUrlLandscape,
        imageUrlTiny = imageUrlTiny
    )

fun WallpaperEntity.toDomain() =
    Wallpaper(
        id = id,
        height = height,
        url = url,
        photographer = photographer,
        categoryName = categoryName,
        isFavorite = isFavorite,
        updatedAt = updatedAt,
        imageUrlPortrait = imageUrlPortrait,
        imageUrlLandscape = imageUrlLandscape,
        imageUrlTiny = imageUrlTiny
    )