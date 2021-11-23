package com.adwi.search

import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.adwi.core.IoDispatcher
import com.adwi.core.base.BaseViewModel
import com.adwi.core.util.ext.onDispatcher
import com.adwi.domain.Wallpaper
import com.adwi.repository.settings.SettingsRepository
import com.adwi.repository.wallpaper.WallpaperRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val wallpaperRepository: WallpaperRepository,
    private val settingsRepository: SettingsRepository,
//    private val savedStateHandle: SavedStateHandle,
//    private val logger: Logger,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    fun onTriggerEvent(event: SearchEvents) {
        when (event) {
            SearchEvents.GetSearch -> {
            }
            is SearchEvents.OnFavoriteClick -> {
                onFavoriteClick(event.wallpaper)
            }
            is SearchEvents.OnSearchQuerySubmit -> {
                onSearchQuerySubmit(event.query)
            }
            SearchEvents.RestoreLastQuery -> {
                restoreLastQuery()
            }
            is SearchEvents.UpdateSavedQuery -> {
                updateSavedQuery(event.query)
            }
            is SearchEvents.ShowMessageEvent -> setEvent(event.event)
        }
    }

    val currentQuery = MutableStateFlow<String?>(null)

    var newQueryInProgress = false
    var pendingScrollToTopAfterNewQuery = false

    val searchResults = currentQuery.flatMapLatest { query ->
        query?.let {
            wallpaperRepository.getSearch(query)
        } ?: emptyFlow()
    }.cachedIn(viewModelScope)

    private fun onSearchQuerySubmit(query: String) {
        currentQuery.value = query
        newQueryInProgress = true
        pendingScrollToTopAfterNewQuery = true
        updateSavedQuery(query)
    }

    private fun updateSavedQuery(query: String) {
        onDispatcher(ioDispatcher) { settingsRepository.updateLastQuery(query) }
    }

    private fun restoreLastQuery() {
        onDispatcher(ioDispatcher) {
            currentQuery.value = settingsRepository.getSettings().first().lastQuery
            newQueryInProgress = false
            pendingScrollToTopAfterNewQuery = false
        }
    }

    private fun onFavoriteClick(wallpaper: Wallpaper) {
        val isFavorite = wallpaper.isFavorite
        wallpaper.isFavorite = !isFavorite
        onDispatcher(ioDispatcher) {
            wallpaperRepository.updateWallpaper(wallpaper)
        }
    }
}