package com.adwi.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.adwi.components.domain.ColorsState
import com.adwi.components.domain.WallpaperListState
import com.adwi.components.domain.WallpaperState
import com.adwi.core.base.BaseViewModel
import com.adwi.core.domain.DataState
import com.adwi.core.util.CalendarUtil
import com.adwi.core.util.Logger
import com.adwi.datasource.local.domain.toDomain
import com.adwi.domain.Wallpaper
import com.adwi.interactors.wallpaper.WallpaperInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val interactors: WallpaperInteractors,
//    private val savedStateHandle: SavedStateHandle,
    private val logger: Logger
) : BaseViewModel() {

    val wallpaperState: MutableState<WallpaperState> = mutableStateOf(WallpaperState())
    val colorsState: MutableState<ColorsState> = mutableStateOf(ColorsState())
    val curatedState: MutableState<WallpaperListState> = mutableStateOf(WallpaperListState())

    init {
        getDaily()
        getColors()
        getCurated()
    }

    private fun getDaily() {
//        onDispatcher(ioDispatcher) {
        interactors.getDaily.execute().onEach { resource ->
            when (resource) {
                is DataState.Error -> {
                    logger.log(resource.error?.localizedMessage ?: "Resource - error")
                }
                is DataState.Success -> {
                    wallpaperState.value =
                        wallpaperState.value.copy(
                            wallpaper = getTodayDaily(
                                list = resource.data?.map { it.toDomain() } ?: listOf()
                            )
                        )
                }
                is DataState.Loading -> {
                    wallpaperState.value =
                        wallpaperState.value.copy(progressBarState = resource.progressBarState)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getColors() {
//        onDispatcher(ioDispatcher) {
        interactors.getColors.execute().onEach { resource ->
            when (resource) {
                is DataState.Error -> {
                    logger.log(resource.error?.localizedMessage ?: "Resource - error")
                }
                is DataState.Success -> {
                    colorsState.value =
                        colorsState.value.copy(
                            categories = resource.data?.map { it.toDomain() } ?: listOf()
                        )
                }
                is DataState.Loading -> {
                    colorsState.value =
                        colorsState.value.copy(progressBarState = resource.progressBarState)
                }
            }
        }.launchIn(viewModelScope)
//        }
    }

    private fun getCurated() {
//        onDispatcher(ioDispatcher) {
        interactors.getCurated.execute().onEach { resource ->
            when (resource) {
                is DataState.Error -> {
                    logger.log(resource.error?.localizedMessage ?: "Resource - error")
                }
                is DataState.Success -> {
                    curatedState.value =
                        curatedState.value.copy(
                            wallpapers = resource.data?.map { it.toDomain() } ?: listOf()
                        )
                }
                is DataState.Loading -> {
                    curatedState.value =
                        curatedState.value.copy(progressBarState = resource.progressBarState)
                }
            }
        }.launchIn(viewModelScope)
//        }
    }

    private fun getTodayDaily(list: List<Wallpaper>): Wallpaper {
        val day: Int = CalendarUtil.getDayOfMonthNumber()
        return list[day]
    }
}