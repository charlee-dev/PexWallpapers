package com.adwi.interactors.wallpaper.usecases

import com.adwi.core.domain.DataState
import com.adwi.core.domain.ProgressBarState
import com.adwi.core.domain.UIComponent
import com.adwi.datasource.local.WallpaperDatabase
import com.adwi.datasource.local.domain.toDomain
import com.adwi.datasource.local.domain.toEntity
import com.adwi.domain.Wallpaper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWallpaper @Inject constructor(
    database: WallpaperDatabase
) {
    val dao = database.wallpaperDao()

    fun getWallpaperById(
        id: Int,
    ): Flow<DataState<Wallpaper>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            // emit data from network
            val wallpaperEntity = dao.getWallpaperById(id)

            emit(DataState.Data(wallpaperEntity.toDomain()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Response(
                    uiComponent = UIComponent.Dialog(
                        title = "Error",
                        description = e.message ?: "Unknown error"
                    )
                )
            )
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }

    suspend fun update(wallpaper: Wallpaper) {
        dao.updateWallpaper(wallpaper.toEntity())
    }
}