package com.adwi.interactors.wallpaper.usecases

import androidx.room.withTransaction
import com.adwi.core.domain.DataState
import com.adwi.core.util.Constants.DEFAULT_DAILY_CATEGORY
import com.adwi.datasource.local.WallpaperDatabase
import com.adwi.datasource.local.domain.WallpaperEntity
import com.adwi.datasource.local.domain.toDaily
import com.adwi.datasource.local.domain.toEntity
import com.adwi.datasource.network.PexService
import com.adwi.interactors.common.keepFavorites
import com.adwi.interactors.common.networkBoundResource
import com.adwi.interactors.common.shouldFetch
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class GetDaily @Inject constructor(
    private val service: PexService,
    private val database: WallpaperDatabase
) {
    private val dailyDao = database.dailyDao()
    private val wallpapersDao = database.wallpaperDao()

    fun execute(
        categoryName: String = DEFAULT_DAILY_CATEGORY
    ): Flow<DataState<List<WallpaperEntity>>> = networkBoundResource(
        query = { dailyDao.getAllDailyWallpapers() },
        fetch = { service.getDailyWallpapers(categoryName = categoryName).wallpaperList },
        saveFetchResult = { remoteList ->
            val favoriteWallpapers = wallpapersDao.getAllFavorites()

            val wallpaperList = remoteList.keepFavorites(
                categoryName = categoryName,
                favoritesList = favoriteWallpapers
            )

            val dailyList = wallpaperList.map { it.toDaily() }

            database.withTransaction {
                wallpapersDao.insertWallpapers(wallpaperList.map { it.toEntity() })
                dailyDao.insertDailyWallpapers(dailyList)
            }
        },
        shouldFetch = { list ->
            if (list.isEmpty()) true else list.shouldFetch()
        }
    )
}