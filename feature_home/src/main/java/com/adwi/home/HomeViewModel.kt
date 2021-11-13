package com.adwi.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
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
@ExperimentalPagingApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val interactors: WallpaperInteractors,
//    private val savedStateHandle: SavedStateHandle,
    private val logger: Logger
) : BaseViewModel() {

    val dailyState: MutableState<WallpaperState> = mutableStateOf(WallpaperState())
    val colorsState: MutableState<ColorsState> = mutableStateOf(ColorsState())
    val curatedState: MutableState<WallpaperListState> = mutableStateOf(WallpaperListState())

    init {
        getDaily()
        getColors()
        getCurated()
    }

    init {
        onTriggerEvent(HomeEvents.GetDaily)
        onTriggerEvent(HomeEvents.GetColors)
        onTriggerEvent(HomeEvents.GetCurated)
    }

    fun onTriggerEvent(event: HomeEvents) {
        when (event) {
            HomeEvents.GetDaily -> getDaily()
            HomeEvents.GetColors -> getColors()
            HomeEvents.GetCurated -> getCurated()
        }
    }

    private fun getDaily() {
        interactors.getDaily.execute().onEach { resource ->
            when (resource) {
                is DataState.Response -> {
                    logger.log(resource.error?.localizedMessage ?: "Resource - error")
                }
                is DataState.Data -> {
                    dailyState.value =
                        dailyState.value.copy(
                            wallpaper = getTodayDaily(
                                list = resource.data?.map { it.toDomain() } ?: listOf()
                            )
                        )
                }
                is DataState.Loading -> {
                    dailyState.value =
                        dailyState.value.copy(progressBarState = resource.progressBarState)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getColors() {
        interactors.getColors.execute().onEach { resource ->
            when (resource) {
                is DataState.Response -> {
                    logger.log(resource.error?.localizedMessage ?: "Resource - error")
                }
                is DataState.Data -> {
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
    }

    private fun getCurated() {
        interactors.getCurated.execute().onEach { resource ->
            when (resource) {
                is DataState.Response -> {
                    logger.log(resource.error?.localizedMessage ?: "Resource - error")
                }
                is DataState.Data -> {
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
    }

    private fun getTodayDaily(list: List<Wallpaper>): Wallpaper {
        val day: Int = CalendarUtil.getDayOfMonthNumber()
        return list[day]
    }
}