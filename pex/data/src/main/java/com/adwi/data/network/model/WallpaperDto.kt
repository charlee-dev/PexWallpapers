package com.adwi.data.network.model

import com.adwi.domain.Wallpaper
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WallpaperDto(
    val id: Int,
    val width: Int,
    val height: Int,
    @SerialName("url")
    val pexUrl: String,
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

fun WallpaperDto.toDomainModel(
    categoryName: String
) = Wallpaper(
    id = this.id.toLong(),
    photographer = this.photographer,
    height = heights.random(),
    url = this.pexUrl,
    isFavorite = false,
    categoryName = categoryName,
    imageUrlPortrait = this.src.portrait,
    imageUrlLandscape = this.src.landscape,
    imageUrlTiny = this.src.tiny
)

private val heights = listOf(830, 1220, 975, 513, 600, 790)