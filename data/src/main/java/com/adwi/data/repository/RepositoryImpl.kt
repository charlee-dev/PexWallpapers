package com.adwi.data.repository

import com.adwi.base.Resource
import com.adwi.data.database.model.Wallpaper
import com.adwi.data.network.service.PexService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class RepositoryImpl @Inject constructor(
    private val service: PexService
) : Repository {
    override fun getCuratedWallpapers(): Flow<Resource<List<Wallpaper>>> {
        TODO("Not yet implemented")
    }
}