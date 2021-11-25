package com.adwi.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.adwi.core.IoDispatcher
import com.adwi.core.base.BaseViewModel
import com.adwi.core.util.ext.onDispatcher
import com.adwi.domain.Wallpaper
import com.adwi.repository.wallpaper.WallpaperRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val wallpaperRepository: WallpaperRepository,
    private val savedStateHandle: SavedStateHandle,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val currentQuery = MutableStateFlow<String>("")

    var newQueryInProgress = false
    var pendingScrollToTopAfterNewQuery = false

    val searchResults = currentQuery.flatMapLatest { query ->
        wallpaperRepository.getSearch(query)
    }.cachedIn(viewModelScope)

    init {
//        restoreLastQuery()
    }

    fun onSearchQuerySubmit(query: String) {
        currentQuery.value = query
        newQueryInProgress = true
        pendingScrollToTopAfterNewQuery = true
        setQuery(currentQuery.value)
    }

    private fun setQuery(query: String) {
        Timber.tag(tag).d("save currentQuery = $query")
        savedStateHandle["query"] = query
    }

    fun restoreSavedQuery() {
        val query = savedStateHandle.get<String>("query")
        query?.let {
            currentQuery.value = query
        }
        Timber.tag(tag).d("restore currentQuery = $query")
    }

    fun onFavoriteClick(wallpaper: Wallpaper) {
        val isFavorite = wallpaper.isFavorite
        wallpaper.isFavorite = !isFavorite
        onDispatcher(ioDispatcher) {
            wallpaperRepository.updateWallpaper(wallpaper)
        }
    }
}