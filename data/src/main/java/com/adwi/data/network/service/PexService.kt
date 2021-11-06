package com.adwi.data.network.service

import com.adwi.domain.Wallpaper

interface PexService {

    suspend fun getCuratedWallpapers(): List<Wallpaper>
}