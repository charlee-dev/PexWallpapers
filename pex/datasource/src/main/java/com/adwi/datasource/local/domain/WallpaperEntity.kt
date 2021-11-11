package com.adwi.datasource.local.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adwi.domain.Wallpaper

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

fun Wallpaper.toCurated() = CuratedEntity(this.id)

fun Wallpaper.toDaily() = DailyEntity(this.id)

fun Wallpaper.toEntity() =
    WallpaperEntity(
        id = this.id,
        photographer = this.photographer,
        height = this.height,
        url = this.url,
        isFavorite = isFavorite,
        categoryName = categoryName,
        updatedAt = this.updatedAt,
        imageUrlLandscape = this.imageUrlLandscape,
        imageUrlPortrait = this.imageUrlPortrait,
        imageUrlTiny = this.imageUrlTiny
    )

fun WallpaperEntity.toDomain() =
    Wallpaper(
        id = this.id,
        photographer = this.photographer,
        height = this.height,
        url = this.url,
        isFavorite = isFavorite,
        categoryName = categoryName,
        updatedAt = this.updatedAt,
        imageUrlLandscape = this.imageUrlLandscape,
        imageUrlPortrait = this.imageUrlPortrait,
        imageUrlTiny = this.imageUrlTiny
    )