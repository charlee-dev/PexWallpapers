package com.adwi.pexwallpapers.data.wallpapers.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import com.adwi.pexwallpapers.data.wallpapers.database.WallpaperDatabase
import com.adwi.pexwallpapers.data.wallpapers.database.domain.*
import com.adwi.pexwallpapers.data.wallpapers.network.PexService
import com.adwi.pexwallpapers.data.wallpapers.network.domain.toEntity
import com.adwi.pexwallpapers.data.wallpapers.repository.util.keepFavorites
import com.adwi.pexwallpapers.data.wallpapers.repository.util.networkBoundResource
import com.adwi.pexwallpapers.data.wallpapers.repository.util.shouldFetch
import com.adwi.pexwallpapers.data.wallpapers.repository.util.shouldFetchColors
import com.adwi.pexwallpapers.model.ColorCategory
import com.adwi.pexwallpapers.model.Wallpaper
import com.adwi.pexwallpapers.util.Constants
import com.adwi.pexwallpapers.util.domain.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class WallpaperRepositoryImpl @Inject constructor(
    private val database: WallpaperDatabase,
    private val service: PexService
) : WallpaperRepository {

    private val wallpapersDao = database.wallpaperDao()
    private val searchDao = database.searchDao()
    private val dailyDao = database.dailyDao()
    private val categoryDao = database.categoryDao()

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
        onFetchFailed = { t ->
            if (t !is HttpException && t !is IOException) {
                throw t
            }
            onFetchRemoteFailed(t)
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
        onFetchFailed = { t ->
            if (t !is HttpException && t !is IOException) {
                throw t
            }
            onFetchRemoteFailed(t)
        }
    )

    override fun getColors(
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchRemoteFailed: (Throwable) -> Unit
    ): Flow<DataState<List<ColorCategory>>> = networkBoundResource(
        query = {
            val entities = categoryDao.getAllColors()
            val categories = entities.map { it.toDomainList() }
            categories
        },
        fetch = {
            val colorList = mutableListOf<ColorCategoryEntity>()
            val wallpaperList = mutableListOf<WallpaperEntity>()

            colorNameList.forEach { color ->
                val response = service.getColor(color)
                val wallpapers = response.wallpaperList

                wallpapers.forEach {
                    val wallpaper = it.toEntity(color)
                    wallpaperList.add(wallpaper)
                }

                colorList += ColorCategoryEntity(
                    name = color,
                    firstImage = wallpapers[0].src.tiny,
                    secondImage = wallpapers[1].src.tiny,
                    thirdImage = wallpapers[2].src.tiny,
                    forthImage = wallpapers[3].src.tiny,
                    timeStamp = System.currentTimeMillis()
                )
            }

            wallpapersDao.insertWallpapers(wallpaperList)

            colorList.toList()
        },
        saveFetchResult = { categoryDao.insertColors(it) },
        shouldFetch = { list ->
            if (forceRefresh) true else list.shouldFetchColors()
        },
        onFetchSuccess = onFetchSuccess,
        onFetchFailed = { t ->
            if (t !is HttpException && t !is IOException) {
                throw t
            }
            onFetchRemoteFailed(t)
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
        onFetchFailed = { t ->
            if (t !is HttpException && t !is IOException) {
                throw t
            }
            onFetchRemoteFailed(t)
        }
    )

    override fun getSearch(query: String): Flow<PagingData<WallpaperEntity>> =
        Pager(
            config = PagingConfig(
                pageSize = Constants.PAGING_SIZE,
                maxSize = Constants.PAGING_MAX_SIZE
            ),
            remoteMediator = SearchNewsRemoteMediator(query, service, database),
            pagingSourceFactory = { searchDao.getSearchResultWallpaperPaged(query) }
        ).flow

    override fun getWallpaperById(id: Int): Flow<Wallpaper> =
        wallpapersDao.getWallpaperById(id).map { it.toDomain() }

    override fun getFavorites(): Flow<List<Wallpaper>> =
        wallpapersDao.getAllFavorites().map { it.toDomainList() }

    override suspend fun updateWallpaper(wallpaper: Wallpaper) {
        wallpapersDao.updateWallpaper(wallpaper.toEntity())
    }

    override suspend fun updateWallpaperIsFavorite(id: Int, isFavorite: Boolean) {
        wallpapersDao.updateWallpaperIsFavorite(id, isFavorite)
    }

    override suspend fun deleteNonFavoriteWallpapersOlderThan(timestampInMillis: Long) =
        wallpapersDao.deleteNonFavoriteWallpapersOlderThan(timestampInMillis)

    override suspend fun resetAllFavorites() = wallpapersDao.resetAllFavorites()

    private val colorNameList = listOf(
        "Purple",
        "Orange",
        "White",
        "Black and White",
        "Violet",
        "Pink",
        "Yellow",
        "Blue",
        "Green",
        "White",
        "Black"
    )
}