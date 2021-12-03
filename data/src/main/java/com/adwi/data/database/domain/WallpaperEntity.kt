package com.adwi.data.database.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adwi.pexwallpapers.domain.model.Wallpaper

@Entity(tableName = "wallpaper_table")
data class WallpaperEntity(
    @PrimaryKey val id: Int = 0,
    var height: Int = 0,
    val url: String = "",
    val photographer: String = "",
    val imageUrl: String = "",
    val categoryName: String = "",
    var isFavorite: Boolean = false,
    val imageUrlPortrait: String = "",
    val imageUrlLandscape: String = "",
    val imageUrlTiny: String = "",
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "curated_wallpapers")
data class CuratedEntity(
    @PrimaryKey val wallpaperId: Int
)

@Entity(tableName = "daily_table")
data class DailyEntity(
    @PrimaryKey val wallpaperId: Int
)

fun Wallpaper.toCurated() = CuratedEntity(id)

fun Wallpaper.toDaily() = DailyEntity(id)

fun Wallpaper.toEntity() =
    WallpaperEntity(
        id = id,
        photographer = photographer,
        height = height,
        url = url,
        isFavorite = isFavorite,
        categoryName = categoryName,
        updatedAt = updatedAt,
        imageUrlLandscape = imageUrlLandscape,
        imageUrlPortrait = imageUrlPortrait,
        imageUrlTiny = imageUrlTiny
    )

fun WallpaperEntity.toDomain() =
    Wallpaper(
        id = id,
        photographer = photographer,
        height = height,
        url = url,
        isFavorite = isFavorite,
        categoryName = categoryName,
        updatedAt = updatedAt,
        imageUrlLandscape = imageUrlLandscape,
        imageUrlPortrait = imageUrlPortrait,
        imageUrlTiny = imageUrlTiny
    )

fun List<WallpaperEntity>.toDomainList() = this.map { it.toDomain() }

fun List<Wallpaper>.toEntityList() = this.map { it.toEntity() }