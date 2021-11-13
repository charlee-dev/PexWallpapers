package com.adwi.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.adwi.core.IoDispatcher
import com.adwi.core.base.BaseViewModel
import com.adwi.core.domain.DataState
import com.adwi.core.util.CalendarUtil
import com.adwi.core.util.Logger
import com.adwi.core.util.ext.onDispatcher
import com.adwi.datasource.local.domain.toDomain
import com.adwi.domain.Wallpaper
import com.adwi.interactors.settings.SettingsInteractors
import com.adwi.interactors.wallpaper.WallpaperInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val wallpaperInteractors: WallpaperInteractors,
    private val settingsInteractors: SettingsInteractors,
//    private val savedStateHandle: SavedStateHandle,
    private val logger: Logger,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val state: MutableState<HomeState> = mutableStateOf(HomeState())

    init {
        onTriggerEvent(HomeEvents.Refresh)
    }

    fun onTriggerEvent(event: HomeEvents) {
        when (event) {
            HomeEvents.GetDaily -> {
                logger.log("onTriggerEvent - getDaily")
                getDaily()
            }
            HomeEvents.GetColors -> {
                logger.log("onTriggerEvent - GetColors")
                getColors()
            }
            HomeEvents.GetCurated -> {
                logger.log("onTriggerEvent - GetCurated")
                getCurated()
            }
            is HomeEvents.SetCategory -> {
                logger.log("onTriggerEvent - SetCategory")
                setCategory(event.categoryName)
            }
            HomeEvents.Refresh -> {
                onTriggerEvent(HomeEvents.GetDaily)
                onTriggerEvent(HomeEvents.GetColors)
                onTriggerEvent(HomeEvents.GetCurated)
            }
        }
    }

    private fun getDaily() {
        wallpaperInteractors.getDaily.execute().onEach { resource ->
            state.value.daily.apply {
                when (resource) {
                    is DataState.Loading -> {
                        logger.log("getDaily - SetCategory")
                        value = value.copy(progressBarState = resource.progressBarState)
                    }
                    is DataState.Data -> {
                        logger.log("getDaily - Data - ${resource.data!!.size}")
                        value = value.copy(
                            wallpaper = getTodayDaily(
                                list = resource.data?.map { it.toDomain() } ?: listOf()
                            )
                        )
                    }
                    is DataState.Response -> {
                        logger.log(resource.error?.localizedMessage ?: "getDaily - error")
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getColors() {
        wallpaperInteractors.getColors.execute().onEach { resource ->
            state.value.colors.apply {
                when (resource) {
                    is DataState.Loading -> {
                        value = value.copy(progressBarState = resource.progressBarState)
                    }
                    is DataState.Data -> {
                        value = value.copy(
                            categories = resource.data?.map { it.toDomain() } ?: listOf()
                        )
                    }
                    is DataState.Response -> {
                        logger.log(resource.error?.localizedMessage ?: "getColors - error")
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getCurated() {
        wallpaperInteractors.getCurated.execute().onEach { resource ->
            state.value.curated.apply {
                when (resource) {
                    is DataState.Loading -> {
                        value = value.copy(progressBarState = resource.progressBarState)
                    }
                    is DataState.Data -> {
                        value = value.copy(
                            wallpapers = resource.data?.map { it.toDomain() } ?: listOf()
                        )
                    }
                    is DataState.Response -> {
                        logger.log(resource.error?.localizedMessage ?: "getCurated - error")
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTodayDaily(list: List<Wallpaper>): Wallpaper {
        val day: Int = CalendarUtil.getDayOfMonthNumber()
        return list[day]
    }

    private fun setCategory(categoryName: String) {
        onDispatcher(dispatcher) {
            settingsInteractors.getSettings.updateLastQuery(categoryName)
        }
    }
}