package com.adwi.data.database.model

data class Wallpaper(
    val id: Int = 0,
    val width: Int? = null,
    var height: Int? = null,
    val url: String? = null,
    val photographer: String = "",
    val photographerUrl: String? = null,
    val color: String = "",
    val imageUrl: String = "",
    val categoryName: String = "",
    val src: Src? = null,
    var isFavorite: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis(),
    var isFirst: Boolean = false,
    var isLast: Boolean = false
) {
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