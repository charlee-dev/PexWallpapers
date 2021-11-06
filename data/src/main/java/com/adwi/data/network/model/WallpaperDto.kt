package com.adwi.data.network.model

import com.adwi.base.util.Constants
import com.adwi.data.database.model.Wallpaper
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WallpaperDto(
    val id: Int,
    val width: Int?,
    val height: Int?,
    @SerialName("url")
    val pexUrl: String?,
    val photographer: String,
    @SerialName("photographer_url")
    val photographerUrl: String?,
    @SerialName("avg_color")
    val color: String,
    val src: Src
) {
    @Serializable
    data class Src(
        val original: String,
        val large2x: String,
        val large: String,
        val medium: String,
        val landscape: String,
        val portrait: String,
        val tiny: String
    )
}

fun WallpaperDto.toEntity(
    categoryName: String = Constants.DEFAULT_CATEGORY
) = Wallpaper(
    id = this.id,
    photographer = this.photographer,
    color = this.color,
    imageUrl = this.src.portrait,
    height = heights.random(),
    width = this.width,
    url = this.pexUrl,
    photographerUrl = this.photographerUrl,
    isFavorite = false,
    categoryName = categoryName,
    src = Wallpaper.Src(
        original = this.src.original,
        large2x = this.src.large2x,
        large = this.src.large,
        medium = this.src.medium,
        landscape = this.src.landscape,
        portrait = this.src.portrait,
        tiny = this.src.tiny
    )
)

private val heights = listOf(830, 1220, 975, 513, 600, 790)