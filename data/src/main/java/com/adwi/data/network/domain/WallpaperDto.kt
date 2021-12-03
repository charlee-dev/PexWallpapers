package com.adwi.data.network.domain

import com.adwi.data.Constants.DEFAULT_CATEGORY
import com.adwi.data.database.domain.WallpaperEntity
import com.adwi.pexwallpapers.domain.model.Wallpaper
import com.google.gson.annotations.SerializedName

data class WallpaperDto(
    val id: Int,
    @SerializedName("url")
    val pexUrl: String,
    val photographer: String,
    val src: SrcDto
) {
    data class SrcDto(
        val original: String,
        val medium: String,
        val landscape: String,
        val portrait: String,
        val tiny: String
    )
}

fun WallpaperDto.toDomain(
    categoryName: String = DEFAULT_CATEGORY,
    isFavorite: Boolean = false
) = Wallpaper(
    id = this.id,
    height = heights,
    url = this.pexUrl,
    photographer = photographer,
    categoryName = categoryName,
    isFavorite = isFavorite,
    updatedAt = System.currentTimeMillis(),
    imageUrlPortrait = this.src.portrait,
    imageUrlLandscape = this.src.original,
    imageUrlTiny = this.src.tiny
)

fun WallpaperDto.toEntity(
    categoryName: String = DEFAULT_CATEGORY,
    isFavorite: Boolean = false
) = WallpaperEntity(
    id = this.id,
    height = heights,
    url = this.pexUrl,
    photographer = photographer,
    categoryName = categoryName,
    isFavorite = isFavorite,
    updatedAt = System.currentTimeMillis(),
    imageUrlPortrait = this.src.portrait,
    imageUrlLandscape = this.src.medium,
    imageUrlTiny = this.src.tiny
)

private val heights = listOf(830, 1220, 975, 513, 600, 790).random()