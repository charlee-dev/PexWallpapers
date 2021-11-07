package com.adwi.domain

data class Wallpaper(
    val id: Long = 0L,
    var height: Int = 0,
    val url: String = "",
    val photographer: String = "",
    val categoryName: String = "",
    var isFavorite: Boolean = false,
    val updatedAt: Long = 0L,
    val imageUrlPortrait: String = "",
    val imageUrlLandscape: String = "",
    val imageUrlTiny: String = ""
)
