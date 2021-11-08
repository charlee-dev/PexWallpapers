package com.adwi.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.adwi.base.BaseViewModel
import com.adwi.core.domain.WallpaperListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : BaseViewModel() {

    val state: MutableState<WallpaperListState> = mutableStateOf(WallpaperListState())

//        val getCurated = WallpaperUseCases.build(
//            sqlDriver = AndroidSqliteDriver(
//                schema = WallpaperUseCases.schema,
//                context = this,
//                name = WallpaperUseCases.dbName,
//            )
//        ).getCuratedWallpapers
//
//        val logger = Logger("GetCuratedWallpapers")
//
//        getCurated.execute().onEach { dataState ->
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
//                    state.value = state.value.copy(wallpapers = dataState.data ?: listOf())
//                }
//                is DataState.Loading -> {
//                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
//                }
//            }
//        }.launchIn(CoroutineScope(Dispatchers.IO))
}