package com.adwi.pexwallpapers.data.wallpapers.database.entity

import com.adwi.pexwallpapers.data.wallpapers.database.domain.ColorCategoryEntity

object CategoryMock {

    val first = ColorCategoryEntity(
        name = "Orange",
        firstImage = "image1",
        secondImage = "image2",
        thirdImage = "image3",
        forthImage = "image4"
    )

    val second = ColorCategoryEntity(
        name = "Purple",
        firstImage = "image1",
        secondImage = "image2",
        thirdImage = "image3",
        forthImage = "image4"
    )
}