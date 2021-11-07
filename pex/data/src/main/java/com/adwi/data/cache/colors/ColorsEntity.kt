package com.adwi.data.cache.colors

import com.adwi.data.cache.ColorsEntity
import com.adwi.data.network.model.WallpaperDto

fun categoryWallpaperListToSingleCategory(colorName: String, wallpapers: List<WallpaperDto>) =
    ColorsEntity(
        name = colorName,
        firstImage = wallpapers[0].src.tiny,
        secondImage = wallpapers[1].src.tiny,
        thirdImage = wallpapers[2].src.tiny,
        forthImage = wallpapers[3].src.tiny,
        timeStamp = System.currentTimeMillis()
    )