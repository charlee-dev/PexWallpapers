package com.adwi.interactors.wallpaper

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import com.adwi.core.domain.Resource
import com.adwi.core.util.Constants
import com.adwi.datasource.local.WallpaperDatabase
import com.adwi.datasource.local.domain.*
import com.adwi.datasource.network.PexService
import com.adwi.datasource.network.domain.toEntity
import com.adwi.domain.ColorCategory
import com.adwi.domain.Wallpaper
import com.adwi.interactors.util.keepFavorites
import com.adwi.interactors.util.networkBoundResource
import com.adwi.interactors.util.shouldFetch
import com.adwi.interactors.util.shouldFetchColors
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
    ): Flow<Resource<List<Wallpaper>>> = networkBoundResource(
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
            val favoriteWallpapers = wallpapersDao.getAllFavorites()
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
    ): Flow<Resource<List<Wallpaper>>> = networkBoundResource(
        query = {
            val entities = dailyDao.getAllDailyWallpapers()
            val wallpapers = entities.map { it.toDomainList() }
            wallpapers
        },
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
    ): Flow<Resource<List<ColorCategory>>> = networkBoundResource(
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
    ): Flow<Resource<List<Wallpaper>>> = networkBoundResource(
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
            val favoriteWallpapers = wallpapersDao.getAllFavorites()
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

    override fun getWallpaperById(id: Int): Flow<Wallpaper> = flow {
        val entity = wallpapersDao.getWallpaperById(id)
        val wallpaper = entity.toDomain()
        emit(wallpaper)
    }

    override fun getFavorites(): Flow<List<Wallpaper>> = flow {
        val entities = wallpapersDao.getAllFavorites()
        val favorites = entities.toDomainList()
        emit(favorites)
    }

    override suspend fun updateWallpaper(wallpaper: Wallpaper) {
        wallpapersDao.updateWallpaper(wallpaper.toEntity())
    }

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