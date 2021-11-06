package com.adwi.data.repository

import com.adwi.base.Resource
import com.adwi.data.database.model.Wallpaper
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getCuratedWallpapers(): Flow<Resource<List<Wallpaper>>>
}