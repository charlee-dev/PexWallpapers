package com.adwi.interactors.wallpaper.usecases

import com.adwi.core.domain.DataState
import com.adwi.core.domain.LoadingState
import com.adwi.core.domain.UIComponent
import com.adwi.datasource.local.dao.WallpapersDao
import com.adwi.datasource.local.domain.toDomain
import com.adwi.domain.Wallpaper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavorites @Inject constructor(
    private val dao: WallpapersDao
) {
    fun execute(): Flow<DataState<List<Wallpaper>>> = flow {
        try {
            emit(DataState.Loading(loadingState = LoadingState.Loading))

            val favorites = dao.getAllFavorites()

            emit(DataState.Data(data = favorites.map { it.toDomain() }))
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
            emit(DataState.Loading(loadingState = LoadingState.Idle))
        }
    }
}