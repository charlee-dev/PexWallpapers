package com.adwi.home

import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.adwi.core.IoDispatcher
import com.adwi.core.base.BaseViewModel
import com.adwi.core.domain.DataState
import com.adwi.core.domain.Event
import com.adwi.core.domain.Refresh
import com.adwi.core.util.ext.onDispatcher
import com.adwi.domain.Wallpaper
import com.adwi.repository.settings.SettingsRepositoryImpl
import com.adwi.repository.wallpaper.WallpaperRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val wallpaperRepository: WallpaperRepositoryImpl,
    private val settingsRepository: SettingsRepositoryImpl,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val dailyList = refreshTrigger.flatMapLatest { refresh ->
        getDaily(refresh == Refresh.FORCE)
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    val colorList = refreshTrigger.flatMapLatest { refresh ->
        getColors(refresh == Refresh.FORCE)
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    val curatedList = refreshTrigger.flatMapLatest { refresh ->
        getCurated(refresh == Refresh.FORCE)
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    init {
        deleteOldNonFavoriteWallpapers()
    }

    fun onTriggerEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.SetCategory -> setCategory(event.categoryName)
            is HomeEvent.OnFavoriteClick -> onFavoriteClick(event.wallpaper)
            HomeEvent.ManualRefresh -> onManualRefresh()
            HomeEvent.DeleteOldNonFavoriteWallpapers -> deleteOldNonFavoriteWallpapers()
            HomeEvent.OnStart -> onStart()
            is HomeEvent.ShowMessageEvent -> setEvent(event.event)
        }
    }

    private fun getCurated(refresh: Boolean) = wallpaperRepository.getCurated(
        forceRefresh = refresh,
        onFetchSuccess = {
            Timber.tag("HomeViewModel").d("getCurated - success")
            setIsRefreshing(false)
            pendingScrollToTopAfterRefresh = true
        },
        onFetchRemoteFailed = {
            setIsRefreshing(false)
            setEvent(Event.ShowErrorMessage(it))
        }
    )

    private fun getColors(refresh: Boolean) = wallpaperRepository.getColors(
        forceRefresh = refresh,
        onFetchSuccess = {
            Timber.tag("HomeViewModel").d("getColors - success")
            setIsRefreshing(false)
            pendingScrollToTopAfterRefresh = true
        },
        onFetchRemoteFailed = {
            setIsRefreshing(false)
            setEvent(Event.ShowErrorMessage(it))
        }
    )

    private fun getDaily(refresh: Boolean) = wallpaperRepository.getDaily(
        forceRefresh = refresh,
        onFetchSuccess = {
            Timber.tag("HomeViewModel").d("getDaily - success")
            setIsRefreshing(false)
            pendingScrollToTopAfterRefresh = true
        },
        onFetchRemoteFailed = {
            setIsRefreshing(false)
            setEvent(Event.ShowErrorMessage(it))
        }
    )

    private fun setRefreshTriggerIfCurrentlyNotLoading(refresh: Refresh) {
        if (
            curatedList.value !is DataState.Loading
        ) {
            setRefreshTriggerChannel(refresh)
        }
        if (
            dailyList.value !is DataState.Loading
        ) {
            setRefreshTriggerChannel(refresh)
        }
        if (
            colorList.value !is DataState.Loading
        ) {
            setRefreshTriggerChannel(refresh)
        }
    }

    private fun onStart() {
        setRefreshTriggerIfCurrentlyNotLoading(Refresh.NORMAL)
        setIsRefreshing(false)
        Timber.tag("HomeViewModel").d("onStart")
    }

    private fun onManualRefresh() {
        setRefreshTriggerIfCurrentlyNotLoading(Refresh.FORCE)
        setIsRefreshing(true)
        Timber.tag("HomeViewModel").d("onManualRefresh")
    }

    private fun deleteOldNonFavoriteWallpapers() {
        onDispatcher(ioDispatcher) {
            wallpaperRepository.deleteNonFavoriteWallpapersOlderThan(
                System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7)
            )
        }
    }

    private fun setCategory(categoryName: String) {
        onDispatcher(ioDispatcher) {
            settingsRepository.updateLastQuery(categoryName)
        }
    }

    private fun onFavoriteClick(wallpaper: Wallpaper) {
        val isFavorite = wallpaper.isFavorite
        val newWallpaper = wallpaper.copy(isFavorite = !isFavorite)

        onDispatcher(ioDispatcher) {
            wallpaperRepository.updateWallpaper(newWallpaper)
        }
    }
}