package com.adwi.data.cache.model

import com.adwi.data.cache.CuratedEntity
import com.adwi.domain.Wallpaper

fun wallpaperToCuratedEntity(wallpaper: Wallpaper) =
    CuratedEntity(wallpaper.id)