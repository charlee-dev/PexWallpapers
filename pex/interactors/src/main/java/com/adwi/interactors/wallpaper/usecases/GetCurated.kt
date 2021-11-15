package com.adwi.interactors.wallpaper.usecases

import androidx.room.withTransaction
import com.adwi.core.domain.DataState
import com.adwi.datasource.local.WallpaperDatabase
import com.adwi.datasource.local.domain.WallpaperEntity
import com.adwi.datasource.local.domain.toCurated
import com.adwi.datasource.local.domain.toEntity
import com.adwi.datasource.network.PexService
import com.adwi.interactors.common.keepFavorites
import com.adwi.interactors.common.networkBoundResource
import com.adwi.interactors.common.shouldFetch
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class GetCurated @Inject constructor(
    private val service: PexService,
    private val database: WallpaperDatabase
) {
    private val dao = database.wallpaperDao()

    fun execute(): Flow<DataState<List<WallpaperEntity>>> = networkBoundResource(
        query = { dao.getAllCuratedWallpapers() },
        fetch = { service.getCuratedWallpapers().wallpaperList },
        saveFetchResult = { remoteList ->
            val favoriteWallpapers = dao.getAllFavorites()

            val wallpaperList = remoteList.keepFavorites(favoritesList = favoriteWallpapers)

            val curatedWallpapers = wallpaperList.map { it.toCurated() }

            database.withTransaction {
                dao.deleteAllCuratedWallpapers()
                dao.insertWallpapers(wallpaperList.map { it.toEntity() })
                dao.insertCuratedWallpapers(curatedWallpapers)
            }
        },
        shouldFetch = { list ->
            if (list.isEmpty()) true else list.shouldFetch()
        }
    )
}