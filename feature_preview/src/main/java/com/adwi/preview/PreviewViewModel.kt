package com.adwi.preview

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.adwi.core.base.BaseViewModel
import com.adwi.core.domain.DataState
import com.adwi.interactors.wallpaper.WallpaperInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class PreviewViewModel
@Inject constructor(
    private val interactors: WallpaperInteractors,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    val state: MutableState<PreviewState> = mutableStateOf(PreviewState())

    init {
        savedStateHandle.get<Int>("wallpaperId")?.let { wallpaperId ->
            onTriggerEvent(PreviewEvents.GetWallpaperById(wallpaperId))
        }
    }

    fun onTriggerEvent(event: PreviewEvents) {
        when (event) {
            is PreviewEvents.GetWallpaperById -> getWallpaperById(event.wallpaperId)
        }
    }

    private fun getWallpaperById(id: Int) {
        interactors.getWallpaper.getWallpaperById(id).onEach { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    state.value = state.value.copy(loadingState = dataState.loadingState)
                }
                is DataState.Data -> {
                    state.value = state.value.copy(wallpaper = dataState.data)
                }
                is DataState.Response -> {
                    // TODO(Handle errors)
                }
            }
        }.launchIn(viewModelScope)
    }
}