package com.adwi.pexwallpapers.domain.model

data class Wallpaper(
    val id: Int = 0,
    var height: Int = 0,
    val url: String = "",
    val photographer: String = "",
    val categoryName: String = "",
    var isFavorite: Boolean = false,
    val updatedAt: Long = 0L,
    val imageUrlPortrait: String = "",
    val imageUrlLandscape: String = "",
    val imageUrlTiny: String = ""
) {
    companion object {
        val defaultDaily = Wallpaper(
            imageUrlPortrait = "https://images.pexels.com/photos/2440061/pexels-photo-2440061.jpeg?cs=srgb&dl=pexels-ian-beckley-2440061.jpg&fm=jpg"
        )
    }
}