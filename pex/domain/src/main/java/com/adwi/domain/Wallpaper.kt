package com.adwi.domain

data class Wallpaper(
    val id: Int = 0,
    var height: Int? = null,
    val url: String? = null,
    val photographer: String = "",
    val categoryName: String = "",
    var isFavorite: Boolean = false,
    val updatedAt: Long = 0L,
    val imageUrlPortrait: String = "",
    val imageUrlLandscape: String = "",
    val imageUrlTiny: String = ""
)
