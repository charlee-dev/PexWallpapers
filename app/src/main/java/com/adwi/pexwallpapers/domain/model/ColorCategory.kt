package com.adwi.pexwallpapers.domain.model

data class ColorCategory(
    val name: String = "",
    val firstImage: String = "",
    val secondImage: String = "",
    val thirdImage: String = "",
    val forthImage: String = "",
    val timeStamp: Long = 0L
) {
    companion object {
        val mock = ColorCategory(
            name = "Violet",
            firstImage = "https://images.pexels.com/photos/2690766/pexels-photo-2690766.jpeg?auto=compress&cs=tinysrgb&h=350",
            secondImage = "https://images.pexels.com/photos/1927595/pexels-photo-1927595.jpeg?auto=compress&cs=tinysrgb&h=350",
            thirdImage = "https://images.pexels.com/photos/3109850/pexels-photo-3109850.jpeg?auto=compress&cs=tinysrgb&h=350",
            forthImage = "https://images.pexels.com/photos/4040567/pexels-photo-4040567.jpeg?auto=compress&cs=tinysrgb&h=350"
        )
    }
}