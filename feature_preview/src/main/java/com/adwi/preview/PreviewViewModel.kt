package com.adwi.preview

import androidx.lifecycle.SavedStateHandle
import androidx.paging.ExperimentalPagingApi
import com.adwi.core.IoDispatcher
import com.adwi.core.base.BaseViewModel
import com.adwi.core.util.ext.onDispatcher
import com.adwi.domain.Wallpaper
import com.adwi.repository.wallpaper.WallpaperRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class PreviewViewModel
@Inject constructor(
    private val wallpaperRepository: WallpaperRepositoryImpl,
    private val savedStateHandle: SavedStateHandle,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val wallpaper: MutableStateFlow<Wallpaper> = MutableStateFlow(Wallpaper())

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
        onDispatcher(ioDispatcher) {
            wallpaperRepository.getWallpaperById(id).collect { wallpaper.value = it }
        }
    }
}