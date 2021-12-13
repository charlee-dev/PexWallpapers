package com.adwi.feature_preview.presentation

import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.SavedStateHandle
import androidx.paging.ExperimentalPagingApi
import com.adrianwitaszak.tool_image.ImageManager
import com.adwi.components.IoDispatcher
import com.adwi.components.base.BaseViewModel
import com.adwi.components.ext.onDispatcher
import com.adwi.data.database.dao.WallpapersDao
import com.adwi.data.database.domain.toDomain
import com.adwi.pexwallpapers.domain.model.Wallpaper
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
    private val wallpapersDao: WallpapersDao,
    private val imageManager: ImageManager,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _wallpaper: MutableStateFlow<Wallpaper?> = MutableStateFlow(null)

    val wallpaper = _wallpaper.asStateFlow()

    init {
        savedStateHandle.get<Int>("wallpaperId")?.let { wallpaperId ->
            getWallpaperById(wallpaperId)
        }
    }

    private fun getWallpaperById(id: Int) {
        onDispatcher(ioDispatcher) {
            wallpapersDao.getWallpaperById(id).collect {
                _wallpaper.value = it.toDomain()
            }
        }
    }

    fun onFavoriteClick(wallpaper: Wallpaper) {
        onDispatcher(ioDispatcher) {
            wallpapersDao.updateWallpaperIsFavorite(wallpaper.id, !wallpaper.isFavorite)
            Timber.d("${!wallpaper.isFavorite}")
        }
    }

    fun getCurrentWallpaper(home: Boolean) = imageManager.getCurrentWallpaper(home).asImageBitmap()
}