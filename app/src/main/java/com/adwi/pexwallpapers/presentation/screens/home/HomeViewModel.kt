package com.adwi.pexwallpapers.presentation.screens.home

import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.adwi.components.IoDispatcher
import com.adwi.components.base.BaseViewModel
import com.adwi.components.base.Refresh
import com.adwi.components.ext.onDispatcher
import com.adwi.feature_settings.data.database.SettingsDao
import com.adwi.pexwallpapers.domain.model.Wallpaper
import com.adwi.repository.WallpaperRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val wallpaperRepository: WallpaperRepositoryImpl,
    private val settingsDao: SettingsDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val daily = refreshTrigger.flatMapLatest { refresh ->
        getDaily(refresh == Refresh.FORCE)
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    val colors = refreshTrigger.flatMapLatest { refresh ->
        getColors(refresh == Refresh.FORCE)
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    val curated = refreshTrigger.flatMapLatest { refresh ->
        getCurated(refresh == Refresh.FORCE)
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    var lowRes = false

    init {
        deleteOldNonFavoriteWallpapers()
    }

    private fun getDaily(refresh: Boolean) =
        wallpaperRepository.getDaily(
            forceRefresh = refresh,
            onFetchSuccess = { onFetchSuccess() },
            onFetchRemoteFailed = { onFetchFailed(it) }
        )

    private fun getColors(refresh: Boolean = true) =
        wallpaperRepository.getColors(
            forceRefresh = refresh,
            onFetchSuccess = { onFetchSuccess() },
            onFetchRemoteFailed = { onFetchFailed(it) }
        )


    private fun getCurated(refresh: Boolean = true) =
        wallpaperRepository.getCurated(
            forceRefresh = refresh,
            onFetchSuccess = { onFetchSuccess() },
            onFetchRemoteFailed = { onFetchFailed(it) }
        )

    fun getDataSaverSettings() {
        onDispatcher(ioDispatcher) {
            val settings = settingsDao.getSettings().first()
            lowRes = settings.lowResMiniatures
        }
    }

    private fun setRefreshTriggerIfCurrentlyNotLoading(refresh: Refresh) {
        if (curated.value !is com.adwi.core.DataState.Loading) {
            setRefreshTriggerChannel(refresh)
        }
        if (daily.value !is com.adwi.core.DataState.Loading) {
            setRefreshTriggerChannel(refresh)
        }
        if (colors.value !is com.adwi.core.DataState.Loading) {
            setRefreshTriggerChannel(refresh)
        }
    }

    fun onStart() {
        setRefreshTriggerIfCurrentlyNotLoading(Refresh.NORMAL)
        setIsRefreshing(false)
    }

    fun manualRefresh() {
        setRefreshTriggerIfCurrentlyNotLoading(Refresh.FORCE)
    }

    fun onFavoriteClick(wallpaper: Wallpaper) {
        val isFavorite = wallpaper.isFavorite
        wallpaper.isFavorite = !isFavorite
        onDispatcher(ioDispatcher) {
            wallpaperRepository.updateWallpaper(wallpaper)
        }
    }

    private fun deleteOldNonFavoriteWallpapers() {
        onDispatcher(ioDispatcher) {
            wallpaperRepository.deleteNonFavoriteWallpapersOlderThan(
                System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7)
            )
        }
    }
}