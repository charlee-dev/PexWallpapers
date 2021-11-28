package com.adwi.pexwallpapers.presentation.screens.favorites

import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.adwi.pexwallpapers.data.WallpaperRepositoryImpl
import com.adwi.pexwallpapers.data.database.settings.SettingsDao
import com.adwi.pexwallpapers.domain.model.Wallpaper
import com.adwi.pexwallpapers.presentation.IoDispatcher
import com.adwi.pexwallpapers.presentation.base.BaseViewModel
import com.adwi.pexwallpapers.presentation.util.ext.onDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class FavoritesViewModel @ExperimentalPagingApi
@Inject constructor(
    private val wallpaperRepository: WallpaperRepositoryImpl,
    private val settingsDao: SettingsDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val wallpapers = getFavorites()
        .stateIn(viewModelScope, SharingStarted.Lazily, listOf())

    var lowRes = false

    fun getFavorites() = wallpaperRepository.getFavorites()

    fun onFavoriteClick(wallpaper: Wallpaper) {
        onDispatcher(ioDispatcher) {
            val isFavorite = wallpaper.isFavorite
            val newWallpaper = wallpaper.copy(isFavorite = !isFavorite)
            wallpaperRepository.updateWallpaper(newWallpaper)
        }
    }

    fun getDataSaverSettings() {
        onDispatcher(ioDispatcher) {
            val settings = settingsDao.getSettings().first()
            lowRes = settings.lowResMiniatures
        }
    }
}