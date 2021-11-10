package com.adwi.interactors.wallpaper.usecases

import androidx.room.withTransaction
import com.adwi.core.util.networkBoundResource
import com.adwi.datasource.local.WallpaperDatabase
import com.adwi.datasource.local.domain.toCurated
import com.adwi.datasource.local.domain.toEntity
import com.adwi.datasource.network.PexService
import com.adwi.datasource.network.domain.toDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ExperimentalCoroutinesApi
class WallpaperRepository @Inject constructor(
    private val service: PexService,
    private val database: WallpaperDatabase
) {
    val dao = database.wallpaperDao()

    fun getCurated(
        forceRefresh: Boolean = true,
        onFetchSuccess: () -> Unit,
        onFetchRemoteFailed: (Throwable) -> Unit
    ) =
        networkBoundResource(
            query = {
                dao.getAllCuratedWallpapers()
            },
            fetch = {
                val response = service.getCuratedWallpapers()
                response.wallpaperList
            },
            saveFetchResult = { remoteWallpaperList ->
                // first() collects one list than cancels flow
                val favoriteWallpapers = dao.getAllFavorites().first()

                val wallpaperList =
                    remoteWallpaperList.map { wallpaper ->
                        val isFavorite = favoriteWallpapers.any { favoriteWallpaper ->
                            favoriteWallpaper.id == wallpaper.id
                        }
                        wallpaper.toDomain(isFavorite = isFavorite)
                    }

                val curatedWallpapers = wallpaperList.map { wallpaper ->
                    wallpaper.toCurated()
                }

                database.withTransaction {
                    dao.deleteAllCuratedWallpapers()
                    dao.insertWallpapers(wallpaperList.map { it.toEntity() })
                    dao.insertCuratedWallpapers(curatedWallpapers)
                }
            },
            shouldFetch = { wallpapers ->
                if (forceRefresh) {
                    true
                } else {
                    val sortedWallpapers = wallpapers.sortedBy { wallpaper ->
                        wallpaper.updatedAt
                    }
                    val oldestTimestamp = sortedWallpapers.firstOrNull()?.updatedAt
                    val needsRefresh = oldestTimestamp == null ||
                            oldestTimestamp < System.currentTimeMillis() -
                            TimeUnit.DAYS.toMillis(5)
                    needsRefresh
                }
            },
            onFetchSuccess = onFetchSuccess,
            onFetchFailed = { t ->
                if (t !is HttpException && t !is IOException) {
                    throw t
                }
                onFetchRemoteFailed(t)
            }
        )


//    fun execute(): Flow<DataState<List<Wallpaper>>> = flow {
//        try {
//            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
//
//            val wallpapers: List<Wallpaper> = try {
//                service.getCuratedWallpapers()
//                    .wallpaperList
//                    .map { it.toDomain() }
//            } catch (e: Exception) {
//                e.printStackTrace()
//                emit(
//                    DataState.Response(
//                        uiComponent = UIComponent.Dialog(
//                            title = "Network Data Error",
//                            description = e.message ?: "Unknown Error"
//                        )
//                    )
//                )
//                listOf()
//            }
//
//            logger.log(wallpapers.size.toString())
//            val curated = wallpapers.map { it.toCurated() }
//            val wallpapersEntityList = wallpapers.map { it.toEntity() }
//
//            dao.insertCuratedWallpapers(curated)
//            dao.insertWallpapers(wallpapersEntityList)
//
//            val cachedCurated = dao.getAllWallpapers().map { it.toDomain() }
//            emit(DataState.Data(cachedCurated))
//        } catch (e: Exception) {
//            e.printStackTrace()
//            logger.log(e.message.toString())
//            emit(
//                DataState.Response(
//                    uiComponent = UIComponent.Dialog(
//                        title = "Error",
//                        description = e.message ?: "Unknown error"
//                    )
//                )
//            )
//        } finally {
//            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
//        }
//    }
}