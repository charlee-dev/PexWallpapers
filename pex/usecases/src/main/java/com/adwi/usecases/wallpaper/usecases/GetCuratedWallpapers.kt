package com.adwi.usecases.wallpaper.usecases

import com.adwi.core.domain.DataState
import com.adwi.core.domain.ProgressBarState
import com.adwi.core.domain.UIComponent
import com.adwi.core.util.Logger
import com.adwi.data.network.service.PexService
import com.adwi.domain.Wallpaper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCuratedWallpapers(
    private val service: PexService,
    private val logger: Logger,
    // TODO(add caching)
) {
    fun execute(): Flow<DataState<List<Wallpaper>>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val wallpapers: List<Wallpaper> = try {
                service.getCuratedWallpapers()
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    DataState.Response(
                        uiComponent = UIComponent.Dialog(
                            title = "Network Data Error",
                            description = e.message ?: "Unknown Error"
                        )
                    )
                )
                listOf()
            }

            // TODO(caching)

            emit(DataState.Data(wallpapers))
        } catch (e: Exception) {
            e.printStackTrace()
            logger.log(e.message.toString())
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
}