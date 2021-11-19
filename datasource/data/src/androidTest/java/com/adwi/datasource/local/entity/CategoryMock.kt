package com.adwi.datasource.local.entity

import com.adwi.datasource.local.domain.ColorCategoryEntity

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