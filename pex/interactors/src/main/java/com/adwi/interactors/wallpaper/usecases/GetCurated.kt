package com.adwi.interactors.wallpaper.usecases

import androidx.room.withTransaction
import com.adwi.core.domain.DataState
import com.adwi.core.util.networkBoundResource
import com.adwi.datasource.local.WallpaperDatabase
import com.adwi.datasource.local.domain.WallpaperEntity
import com.adwi.datasource.local.domain.toCurated
import com.adwi.datasource.local.domain.toEntity
import com.adwi.datasource.network.PexService
import com.adwi.datasource.network.domain.toDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ExperimentalCoroutinesApi
class GetCurated @Inject constructor(
    private val service: PexService,
    private val database: WallpaperDatabase
) {
    private val dao = database.wallpaperDao()

    fun execute(): Flow<DataState<List<WallpaperEntity>>> = networkBoundResource(
        query = {
            dao.getAllCuratedWallpapers()
        },
        fetch = {
            val response = service.getCuratedWallpapers()
            response.wallpaperList
        },
        saveFetchResult = { remoteWallpaperList ->
            Timber.d("wallpaperList size - $remoteWallpaperList")
            val favoriteWallpapers = dao.getAllFavorites().first()

                val wallpaperList =
                    remoteWallpaperList.map { wallpaperDto ->
                        val isFavorite = favoriteWallpapers.any { favoriteWallpaper ->
                            favoriteWallpaper.id == wallpaperDto.id
                        }
                        wallpaperDto.toDomain(isFavorite = isFavorite)
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
                if (wallpapers.isEmpty()) {
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
            }
        )
}