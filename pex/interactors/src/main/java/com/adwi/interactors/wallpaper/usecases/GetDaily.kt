package com.adwi.interactors.wallpaper.usecases

import androidx.room.withTransaction
import com.adwi.core.domain.DataState
import com.adwi.core.util.Constants.DAILY_PAGE_SIZE
import com.adwi.core.util.Constants.DEFAULT_DAILY_CATEGORY
import com.adwi.core.util.networkBoundResource
import com.adwi.datasource.local.WallpaperDatabase
import com.adwi.datasource.local.domain.WallpaperEntity
import com.adwi.datasource.local.domain.toDaily
import com.adwi.datasource.local.domain.toEntity
import com.adwi.datasource.network.PexService
import com.adwi.datasource.network.domain.toDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.util.concurrent.TimeUnit
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
        query = {
            dailyDao.getAllDailyWallpapers()
        },
        fetch = {
            val response = service.getDailyWallpapers(categoryName = categoryName)
            response.wallpaperList
        },
        saveFetchResult = { remoteDailyList ->
            val favoriteWallpapers = wallpapersDao.getAllFavorites().first()

            val wallpaperList =
                remoteDailyList.map { wallpaperDto ->
                    val isFavorite = favoriteWallpapers.any { favoriteWallpaper ->
                        favoriteWallpaper.id == wallpaperDto.id
                    }
                    wallpaperDto.toDomain(isFavorite = isFavorite)
                }

            val dailyList = wallpaperList.map { it.toDaily() }

            database.withTransaction {
                wallpapersDao.insertWallpapers(wallpaperList.map { it.toEntity() })
                dailyDao.insertDailyWallpapers(dailyList)
            }
        },
        shouldFetch = { dailyList ->
            if (dailyList.isEmpty()) {
                true
            } else {
                val sortedWallpapers = dailyList.sortedBy { wallpaper ->
                    wallpaper.updatedAt
                }
                val oldestTimestamp = sortedWallpapers.firstOrNull()?.updatedAt
                val needsRefresh = oldestTimestamp == null ||
                        oldestTimestamp < System.currentTimeMillis() -
                        TimeUnit.DAYS.toMillis(DAILY_PAGE_SIZE.toLong())
                needsRefresh
            }
        }
    )
}