package com.adwi.feature_home.repository

import androidx.room.withTransaction
import com.adwi.core.DataState
import com.adwi.core.networkBoundResource
import com.adwi.data.database.WallpaperDatabase
import com.adwi.data.database.domain.*
import com.adwi.data.network.PexService
import com.adwi.data.util.keepFavorites
import com.adwi.data.util.shouldFetch
import com.adwi.data.util.throwIfException
import com.adwi.pexwallpapers.domain.model.Wallpaper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalCoroutinesApi
internal class HomeRepositoryImpl @Inject constructor(
    private val service: PexService,
    private val database: WallpaperDatabase
) : HomeRepository {

    private val wallpapersDao = database.wallpaperDao()
    private val dailyDao = database.dailyDao()

    override fun getCurated(
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchRemoteFailed: (Throwable) -> Unit
    ): Flow<DataState<List<Wallpaper>>> = networkBoundResource(
        query = {
            val entities = wallpapersDao.getAllCuratedWallpapers()
            val wallpapers = entities.map { it.toDomainList() }
            wallpapers
        },
        fetch = {
            val response = service.getCuratedWallpapers()
            response.wallpaperList
        },
        saveFetchResult = { remoteList ->
            val favoriteWallpapers = wallpapersDao.getAllFavorites().first()
            val wallpaperList = remoteList.keepFavorites(favoritesList = favoriteWallpapers)
            val entityList = wallpaperList.map { it.toEntity() }
            val curatedList = wallpaperList.map { it.toCurated() }

            database.withTransaction {
                wallpapersDao.deleteAllCuratedWallpapers()
                wallpapersDao.insertWallpapers(entityList)
                wallpapersDao.insertCuratedWallpapers(curatedList)
            }
        },
        shouldFetch = { list ->
            if (forceRefresh) true else list.shouldFetch()
        },
        onFetchSuccess = onFetchSuccess,
        onFetchFailed = { throwable ->
            throwable.throwIfException()
            onFetchRemoteFailed(throwable)
        }
    )

    override fun getDaily(
        categoryName: String,
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchRemoteFailed: (Throwable) -> Unit
    ): Flow<DataState<List<Wallpaper>>> = networkBoundResource(
        query = {
            val entities = dailyDao.getAllDailyWallpapers()
            val wallpapers = entities.map { it.toDomainList() }
            wallpapers
        },
        fetch = { service.getDailyWallpapers(categoryName = categoryName).wallpaperList },
        saveFetchResult = { remoteList ->
            val favoriteWallpapers = wallpapersDao.getAllFavorites().first()

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
            if (forceRefresh) true else list.shouldFetch()
        },
        onFetchSuccess = onFetchSuccess,
        onFetchFailed = { throwable ->
            throwable.throwIfException()
            onFetchRemoteFailed(throwable)
        }
    )

    override fun getWallpapersOfCategory(
        categoryName: String,
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchRemoteFailed: (Throwable) -> Unit
    ): Flow<DataState<List<Wallpaper>>> = networkBoundResource(
        query = {
            val entities = wallpapersDao.getWallpapersOfCategory(categoryName)
            val wallpapers = entities.map { it.toDomainList() }
            wallpapers
        },
        fetch = {
            val response = service.getSearch(categoryName)
            response.wallpaperList
        },
        saveFetchResult = { remoteList ->
            val favoriteWallpapers = wallpapersDao.getAllFavorites().first()
            val sortedWallpapers = remoteList.keepFavorites(favoritesList = favoriteWallpapers)
            val entityList = sortedWallpapers.toEntityList()

            wallpapersDao.insertWallpapers(entityList)
        },
        shouldFetch = { list ->
            if (forceRefresh) true else list.shouldFetch()
        },
        onFetchSuccess = onFetchSuccess,
        onFetchFailed = { throwable ->
            throwable.throwIfException()
            onFetchRemoteFailed(throwable)
        }
    )

    override suspend fun updateWallpaper(wallpaper: Wallpaper) {
        wallpapersDao.updateWallpaper(wallpaper.toEntity())
    }

    override suspend fun deleteNonFavoriteWallpapersOlderThan(timestampInMillis: Long) =
        wallpapersDao.deleteNonFavoriteWallpapersOlderThan(timestampInMillis)
}