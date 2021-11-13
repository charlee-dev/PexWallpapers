package com.adwi.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.adwi.core.IoDispatcher
import com.adwi.core.base.BaseViewModel
import com.adwi.core.util.Logger
import com.adwi.core.util.ext.onDispatcher
import com.adwi.domain.Wallpaper
import com.adwi.interactors.settings.SettingsInteractors
import com.adwi.interactors.wallpaper.WallpaperInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val wallpaperInteractors: WallpaperInteractors,
    private val settingsInteractors: SettingsInteractors,
//    private val savedStateHandle: SavedStateHandle,
    private val logger: Logger,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val state: MutableState<SearchState> = mutableStateOf(SearchState())


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
        }
    }

    val currentQuery = MutableStateFlow<String?>(null)

    val hasCurrentQuery = currentQuery.map { it != null }

    var refreshInProgress = false
    var pendingScrollToTopAfterRefresh = false
    var newQueryInProgress = false
    var pendingScrollToTopAfterNewQuery = false

    val searchResults = currentQuery.flatMapLatest { query ->
        query?.let {
            wallpaperInteractors.getSearch.getSearchResultsPaged(query)
        } ?: emptyFlow()
    }.cachedIn(viewModelScope)

    init {
        restoreLastQuery()
        currentQuery.value?.let {
            onTriggerEvent(SearchEvents.OnSearchQuerySubmit(currentQuery.value!!))
        }
    }

    fun onSearchQuerySubmit(query: String) {
        currentQuery.value = query
        newQueryInProgress = true
        pendingScrollToTopAfterNewQuery = true
        updateSavedQuery(query)
    }

    private fun updateSavedQuery(query: String) {
        onDispatcher(ioDispatcher) { settingsInteractors.getSettings.updateLastQuery(query) }
    }

    private fun restoreLastQuery() {
        onDispatcher(ioDispatcher) {
            currentQuery.value = settingsInteractors.getSettings.getSettings().first().lastQuery
            newQueryInProgress = false
            pendingScrollToTopAfterNewQuery = false
        }
    }

    fun onFavoriteClick(wallpaper: Wallpaper) {
        val isFavorite = wallpaper.isFavorite
        wallpaper.isFavorite = !isFavorite
        onDispatcher(ioDispatcher) {
            wallpaperInteractors.getWallpaper.update(wallpaper)
        }
    }
}