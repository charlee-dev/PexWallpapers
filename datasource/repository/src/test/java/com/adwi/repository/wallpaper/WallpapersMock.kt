package com.adwi.repository.wallpaper

import com.adwi.pexwallpapers.data.wallpapers.database.domain.CuratedEntity
import com.adwi.domain.Wallpaper

object WallpapersMock {

    val first = Wallpaper(
        id = 1,
        height = 1080,
        url = "test.com/url",
        photographer = "Ad Wi",
        categoryName = "Flowers",
        isFavorite = false,
        updatedAt = 1234567890
    )

    val second = Wallpaper(
        id = 2,
        height = 780,
        url = "test.com/url",
        photographer = "John Smith",
        categoryName = "Cars",
        isFavorite = false,
        updatedAt = 5678901234
    )

    val third = Wallpaper(
        id = 3,
        height = 8100,
        url = "test.com/url",
        photographer = "Peter Parker",
        categoryName = "Flowers",
        isFavorite = false,
        updatedAt = 1289034567
    )

    val wallpaperList = listOf(first, second, third)
}

object CuratedMock {
    val first =
        com.adwi.pexwallpapers.data.wallpapers.database.domain.CuratedEntity(wallpaperId = 2)
}