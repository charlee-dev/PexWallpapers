package com.adwi.interactors.wallpaper.usecases

import com.adwi.datasource.local.WallpaperDatabase
import com.adwi.datasource.local.domain.WallpaperEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class GetPreview @Inject constructor(
    database: WallpaperDatabase
) {
    private val wallpaperDao = database.wallpaperDao()

    fun execute(wallpaperId: Int): Flow<WallpaperEntity> =
        wallpaperDao.getWallpaperById(wallpaperId)
}


