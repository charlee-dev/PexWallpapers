package com.adwi.home

import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.adwi.core.IoDispatcher
import com.adwi.core.base.BaseViewModel
import com.adwi.core.domain.DataState
import com.adwi.core.domain.Refresh
import com.adwi.core.util.ext.onDispatcher
import com.adwi.domain.Wallpaper
import com.adwi.repository.wallpaper.WallpaperRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val wallpaperRepository: WallpaperRepositoryImpl,
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

    private fun setRefreshTriggerIfCurrentlyNotLoading(refresh: Refresh) {
        if (curated.value !is DataState.Loading) {
            setRefreshTriggerChannel(refresh)
        }
        if (daily.value !is DataState.Loading) {
            setRefreshTriggerChannel(refresh)
        }
        if (colors.value !is DataState.Loading) {
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