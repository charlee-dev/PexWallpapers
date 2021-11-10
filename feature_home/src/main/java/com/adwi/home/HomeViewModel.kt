package com.adwi.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.adwi.core.base.BaseViewModel
import com.adwi.core.domain.WallpaperListState
import com.adwi.core.util.Logger
import com.adwi.datasource.local.domain.toDomain
import com.adwi.domain.ColorCategory
import com.adwi.domain.Wallpaper
import com.adwi.interactors.wallpaper.usecases.WallpaperRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val wallpaperRepository: WallpaperRepository,
//    private val savedStateHandle: SavedStateHandle,
    private val logger: Logger
) : BaseViewModel() {

    val curatedState: MutableState<WallpaperListState> = mutableStateOf(WallpaperListState())

    val dailyWallpaper = MutableStateFlow(Wallpaper())

    val colorList = MutableStateFlow<List<ColorCategory>>(emptyList())

    val wallpaperList = MutableStateFlow<List<Wallpaper>>(emptyList())

    init {
        getCurated()
    }

    private fun getCurated() {
        viewModelScope.launch(Dispatchers.IO) {
            wallpaperRepository.getCurated(
                onFetchRemoteFailed = {},
                onFetchSuccess = {}
            ).collect { resource ->
                wallpaperList.value = resource.data?.map { wallpaperEntity ->
                    wallpaperEntity.toDomain()
                } ?: listOf()
            }
        }
//        getCuratedWallpapers.execute().onEach { dataState ->
//            when (dataState) {
//                is DataState.Response -> {
//                    when (dataState.uiComponent) {
//                        is UIComponent.Dialog -> {
//                            logger.log((dataState.uiComponent as UIComponent.Dialog).description)
//                        }
//                        is UIComponent.None -> {
//                            logger.log((dataState.uiComponent as UIComponent.None).message)
//                        }
//                    }
//                }
//                is DataState.Data -> {
//                    curatedState.value =
//                        curatedState.value.copy(wallpapers = dataState.data ?: listOf())
//                }
//                is DataState.Loading -> {
//                    curatedState.value =
//                        curatedState.value.copy(progressBarState = dataState.progressBarState)
//                }
//            }
//        }.launchIn(CoroutineScope(Dispatchers.IO))
    }
}