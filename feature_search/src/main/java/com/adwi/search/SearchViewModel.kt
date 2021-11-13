package com.adwi.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.adwi.components.domain.WallpaperListState
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

    val curatedState: MutableState<WallpaperListState> = mutableStateOf(WallpaperListState())

    init {
        restoreLastQuery()
    }

//    private fun getCurated() {
//        interactors.getCurated.execute().onEach { resource ->
//            when (resource) {
//                is DataState.Error -> {
//                    logger.log(resource.error?.localizedMessage ?: "Resource - error")
//                }
//                is DataState.Success -> {
//                    curatedState.value =
//                        curatedState.value.copy(
//                            wallpapers = resource.data?.map { it.toDomain() } ?: listOf()
//                        )
//                }
//                is DataState.Loading -> {
//                    curatedState.value =
//                        curatedState.value.copy(progressBarState = resource.progressBarState)
//                }
//            }
//        }.launchIn(viewModelScope)
//    }

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
    }

    fun onTriggerEvent(event: SearchEvents) {
        when (event) {
            SearchEvents.GetSearch -> TODO()
            is SearchEvents.OnFavoriteClick -> {

            }
            is SearchEvents.OnSearchQuerySubmit -> TODO()
            SearchEvents.RestoreLastQuery -> TODO()
            is SearchEvents.UpdateSavedQuery -> TODO()
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