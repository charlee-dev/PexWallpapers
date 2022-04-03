package com.adwi.feature_favorites.ui

import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.adwi.base.IoDispatcher
import com.adwi.components.base.BaseViewModel
import com.adwi.base.ext.onDispatcher
import com.adwi.data.database.dao.WallpapersDao
import com.adwi.data.database.domain.toDomainList
import com.adwi.data.database.domain.toEntity
import com.adwi.pexwallpapers.domain.model.Wallpaper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class FavoritesViewModel
@Inject constructor(
    private val wallpaperDao: WallpapersDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val wallpapers = getFavorites()
        .stateIn(viewModelScope, SharingStarted.Lazily, listOf())


    fun getFavorites() = wallpaperDao.getAllFavorites().map { it.toDomainList() }

    fun onFavoriteClick(wallpaper: Wallpaper) {
        onDispatcher(ioDispatcher) {
            val isFavorite = wallpaper.isFavorite
            val newWallpaper = wallpaper.copy(isFavorite = !isFavorite)
            wallpaperDao.updateWallpaper(newWallpaper.toEntity())
        }
    }

    fun resetFavorites() {
        onDispatcher(ioDispatcher) {
            wallpaperDao.resetAllFavorites()
        }
    }
}