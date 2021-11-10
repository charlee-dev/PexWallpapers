package com.adwi.interactors.wallpaper

import com.adwi.interactors.wallpaper.usecases.WallpaperRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
data class WallpaperInteractors constructor(
    val getCuratedWallpapers: WallpaperRepository,
)