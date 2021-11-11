package com.adwi.interactors.wallpaper

import com.adwi.interactors.wallpaper.usecases.GetColors
import com.adwi.interactors.wallpaper.usecases.GetCurated
import com.adwi.interactors.wallpaper.usecases.GetDaily
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
data class WallpaperInteractors @Inject constructor(
    val getCurated: GetCurated,
    val getDaily: GetDaily,
    val getColors: GetColors
)