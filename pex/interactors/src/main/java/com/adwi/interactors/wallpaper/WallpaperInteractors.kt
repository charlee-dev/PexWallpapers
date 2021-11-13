package com.adwi.interactors.wallpaper

import androidx.paging.ExperimentalPagingApi
import com.adwi.interactors.wallpaper.usecases.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
data class WallpaperInteractors
@Inject constructor(
    val getWallpaper: GetWallpaper,
    val getCurated: GetCurated,
    val getDaily: GetDaily,
    val getColors: GetColors,
    val getSearch: GetSearch
)