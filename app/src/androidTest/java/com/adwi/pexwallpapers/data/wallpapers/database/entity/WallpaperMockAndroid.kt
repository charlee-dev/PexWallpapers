package com.adwi.pexwallpapers.data.wallpapers.database.entity

import com.adwi.pexwallpapers.data.wallpapers.database.domain.WallpaperEntity

object WallpaperMockAndroid {

    val first = WallpaperEntity(
        id = 1,
        height = 3024,
        url = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
        photographer = "Joey Farina",
        imageUrl = "test.com/imageUrl",
        categoryName = "Flowers",
        isFavorite = false,
        updatedAt = 163066497202
    )
    val second = WallpaperEntity(
        id = 2,
        height = 3024,
        url = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014425/",
        photographer = "Ad Wi",
        imageUrl = "test.com/imageUrl",
        categoryName = "Cars",
        isFavorite = true,
        updatedAt = 1630664958796
    )
    val third = WallpaperEntity(
        id = 3,
        height = 3024,
        url = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014425/",
        photographer = "Ad Wi",
        imageUrl = "test.com/imageUrl",
        categoryName = "Cars",
        isFavorite = true,
        updatedAt = 1630664975234
    )
    val forth = WallpaperEntity(
        id = 4,
        height = 3024,
        url = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014425/",
        photographer = "Ad Wi",
        imageUrl = "test.com/imageUrl",
        categoryName = "Flowers",
        isFavorite = false,
        updatedAt = 163066495879
    )

    val list = listOf(first, second, third, forth)
}