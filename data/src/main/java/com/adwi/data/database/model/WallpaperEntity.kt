package com.adwi.data.database.model

import com.adwi.domain.Wallpaper

data class WallpaperEntity(
    val id: Int = 0,
    var height: Int? = null,
    val url: String? = null,
    val photographer: String = "",
    val photographerUrl: String? = null,
    val categoryName: String = "",
    var isFavorite: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis(),
    val imageUrlPortrait: String = "",
    val imageUrlLandscape: String = "",
    val imageUrlTiny: String = ""
)

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

fun WallpaperEntity.toEntity() =
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