package com.adwi.feature_home.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.adwi.components.IoDispatcher
import com.adwi.components.base.BaseViewModel
import com.adwi.components.base.Refresh
import com.adwi.components.ext.onDispatcher
import com.adwi.core.DataState
import com.adwi.feature_home.data.HomeRepository
import com.adwi.feature_home.data.category.colorCategoryList
import com.adwi.pexwallpapers.domain.model.Wallpaper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val daily = refreshTrigger.flatMapLatest { refresh ->
        getDaily(refresh == Refresh.FORCE)
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    val colors = MutableStateFlow(colorCategoryList)

    val curated = refreshTrigger.flatMapLatest { refresh ->
        getCurated(refresh == Refresh.FORCE)
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    init {
        deleteOldNonFavoriteWallpapers()
    }

    private fun getDaily(refresh: Boolean) =
        repository.getDaily(
            forceRefresh = refresh,
            onFetchSuccess = { onFetchSuccess() },
            onFetchRemoteFailed = { onFetchFailed(it) }
        )

    private fun getCurated(refresh: Boolean = true) =
        repository.getCurated(
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
            repository.updateWallpaper(wallpaper)
        }
    }

    private fun deleteOldNonFavoriteWallpapers() {
        onDispatcher(ioDispatcher) {
            repository.deleteNonFavoriteWallpapersOlderThan(
                System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7)
            )
        }
    }
}