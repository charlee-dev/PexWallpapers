package com.adwi.data.network.service

import com.adwi.data.database.model.Wallpaper

interface PexService {

    suspend fun getCuratedWallpapers(): List<Wallpaper>
}