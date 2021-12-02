package com.adwi.tests

import com.adwi.pexwallpapers.domain.model.Wallpaper

object WallpapersMock {

    val first = Wallpaper(
        id = 1,
        height = 1080,
        photographer = "Ad Wi",
        url = "test.com/imageUrl",
        categoryName = "Flowers",
        isFavorite = false,
        updatedAt = 1234567890,
        imageUrlLandscape = "landscape",
        imageUrlPortrait = "portrait",
        imageUrlTiny = "tiny"
    )

    val second = Wallpaper(
        id = 2,
        height = 1080,
        photographer = "Ad Wi",
        url = "test.com/imageUrl",
        categoryName = "Cars",
        isFavorite = false,
        updatedAt = 1234567890,
        imageUrlLandscape = "landscape",
        imageUrlPortrait = "portrait",
        imageUrlTiny = "tiny"
    )

    val third = Wallpaper(
        id = 3,
        height = 1080,
        photographer = "Peter Parker",
        url = "test.com/imageUrl",
        categoryName = "Flowers",
        isFavorite = false,
        updatedAt = 1234567890,
        imageUrlLandscape = "landscape",
        imageUrlPortrait = "portrait",
        imageUrlTiny = "tiny"
    )

    val wallpaperList = listOf(first, second, third)
}