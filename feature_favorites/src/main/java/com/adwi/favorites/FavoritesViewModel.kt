package com.adwi.favorites

import androidx.paging.ExperimentalPagingApi
import com.adwi.common.IoDispatcher
import com.adwi.common.base.BaseViewModel
import com.adwi.common.util.ext.onDispatcher
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
class FavoritesViewModel @ExperimentalPagingApi
@Inject constructor(
    private val wallpaperRepository: WallpaperRepositoryImpl,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val wallpapers: MutableStateFlow<List<Wallpaper>> = MutableStateFlow(listOf())

    fun onTriggerEvent(event: FavoritesEvent) {
        when (event) {
            FavoritesEvent.GetFavorites -> getFavorites()

            is FavoritesEvent.OnFavoriteClick -> {
                doFavorite(event.wallpaper)
                getFavorites()
            }
        }
    }

    private fun getFavorites() {
        onDispatcher(ioDispatcher) {
            wallpaperRepository.getFavorites().collect { wallpapers.value = it }
        }
    }

    private fun doFavorite(wallpaper: Wallpaper) {
        onDispatcher(ioDispatcher) {
            val isFavorite = wallpaper.isFavorite
            val newWallpaper = wallpaper.copy(isFavorite = !isFavorite)
            snackBarMessage.value = "Long pressed"
            wallpaperRepository.updateWallpaper(newWallpaper)
        }
    }
}