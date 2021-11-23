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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
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

    val isRefreshing = MutableStateFlow(false)

    val pendingScrollToTopAfterRefresh = MutableStateFlow(false)
    var categoryPendingScrollToTopAfterRefresh = MutableStateFlow(false)
    var curatedPendingScrollToTopAfterRefresh = MutableStateFlow(false)


    private val dailyRefreshTriggerChannel = Channel<Refresh>()
    val dailyRefreshTrigger = dailyRefreshTriggerChannel.receiveAsFlow()

    private val curatedRefreshTriggerChannel = Channel<Refresh>()
    val curatedRefreshTrigger = curatedRefreshTriggerChannel.receiveAsFlow()

    private val colorRefreshTriggerChannel = Channel<Refresh>()
    val colorRefreshTrigger = colorRefreshTriggerChannel.receiveAsFlow()

    val dailyList = dailyRefreshTrigger.flatMapLatest { refresh ->
        getDaily(refresh == Refresh.FORCE)
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    val colorList = colorRefreshTrigger.flatMapLatest { refresh ->
        getColors(refresh == Refresh.FORCE)

    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    val curatedList = curatedRefreshTrigger.flatMapLatest { refresh ->
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
        }
    }

    private fun getCurated(refresh: Boolean) = wallpaperRepository.getCurated(
        forceRefresh = refresh,
        onFetchSuccess = {
            Timber.tag("HomeViewModel").d("getCurated - success")
            isRefreshing.value = false
            pendingScrollToTopAfterRefresh.value = true
        },
        onFetchRemoteFailed = {
            isRefreshing.value = false
            setEvent(Event.ShowErrorMessage(it))
        }
    )

    private fun getColors(refresh: Boolean) = wallpaperRepository.getColors(
        forceRefresh = refresh,
        onFetchSuccess = {
            Timber.tag("HomeViewModel").d("getColors - success")
            isRefreshing.value = false
            pendingScrollToTopAfterRefresh.value = true
        },
        onFetchRemoteFailed = {
            isRefreshing.value = false
            setEvent(Event.ShowErrorMessage(it))
        }
    )

    private fun getDaily(refresh: Boolean) = wallpaperRepository.getDaily(
        forceRefresh = refresh,
        onFetchSuccess = {
            Timber.tag("HomeViewModel").d("getDaily - success")
            isRefreshing.value = false
            pendingScrollToTopAfterRefresh.value = true
        },
        onFetchRemoteFailed = {
            isRefreshing.value = false
            setEvent(Event.ShowErrorMessage(it))
        }
    )

    private fun setRefreshTriggerIfCurrentlyNotLoading(refresh: Refresh) {
        if (
            curatedList.value !is DataState.Loading
        ) {
            onDispatcher(ioDispatcher) {
                curatedRefreshTriggerChannel.send(refresh)
            }
        }
        if (
            dailyList.value !is DataState.Loading
        ) {
            onDispatcher(ioDispatcher) {
                dailyRefreshTriggerChannel.send(refresh)
            }
        }
        if (
            colorList.value !is DataState.Loading
        ) {
            onDispatcher(ioDispatcher) {
                colorRefreshTriggerChannel.send(refresh)
            }
        }
    }

    private fun onStart() {
        setRefreshTriggerIfCurrentlyNotLoading(Refresh.NORMAL)
        Timber.tag("HomeViewModel").d("onStart")
    }

    private fun onManualRefresh() {
        Timber.tag("HomeViewModel").d("onManualRefresh")
        setRefreshTriggerIfCurrentlyNotLoading(Refresh.NORMAL)
        isRefreshing.value = true
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
