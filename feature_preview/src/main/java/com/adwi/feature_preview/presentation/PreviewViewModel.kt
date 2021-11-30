package com.adwi.feature_preview.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.paging.ExperimentalPagingApi
import com.adwi.components.IoDispatcher
import com.adwi.components.base.BaseViewModel
import com.adwi.components.ext.onDispatcher
import com.adwi.core.Resource
import com.adwi.pexwallpapers.domain.model.Wallpaper
import com.adwi.repository.WallpaperRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class PreviewViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val wallpaperRepository: WallpaperRepositoryImpl,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _wallpaper: MutableStateFlow<Wallpaper?> = MutableStateFlow(null)
    private val _saveState: MutableStateFlow<Resource> = MutableStateFlow(Resource.Idle)

    val wallpaper = _wallpaper.asStateFlow()
    val saveState = _saveState.asStateFlow()

    init {
        savedStateHandle.get<Int>("wallpaperId")?.let { wallpaperId ->
            getWallpaperById(wallpaperId)
        }
    }

    private fun getWallpaperById(id: Int) {
        onDispatcher(ioDispatcher) {
            wallpaperRepository.getWallpaperById(id).collect {
                _wallpaper.value = it
            }
        }
    }

    fun onFavoriteClick(wallpaper: Wallpaper) {
        onDispatcher(ioDispatcher) {
            wallpaperRepository.updateWallpaperIsFavorite(wallpaper.id, !wallpaper.isFavorite)
            Timber.d("${!wallpaper.isFavorite}")
        }
    }
}