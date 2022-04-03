package com.adwi.feature_search.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.adwi.base.IoDispatcher
import com.adwi.components.base.BaseViewModel
import com.adwi.base.ext.onDispatcher
import com.adwi.feature_search.domain.repository.SearchRepository
import com.adwi.pexwallpapers.domain.model.Wallpaper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
 class SearchViewModel @Inject constructor(
    private val repository: SearchRepository,
    private val savedStateHandle: SavedStateHandle,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _currentQuery = MutableStateFlow("")
    val currentQuery = _currentQuery.asStateFlow()

    val searchResults = currentQuery.flatMapLatest { query ->
        repository.getSearch(query)
    }.cachedIn(viewModelScope)

    fun onSearchQuerySubmit(query: String) {
        _currentQuery.value = query
        setPendingScrollToTopAfterRefresh(true)
        setQuery(currentQuery.value)
    }

    private fun setQuery(query: String) {
        Timber.tag(tag).d("save currentQuery = $query")
        savedStateHandle["query"] = query
    }

    fun restoreSavedQuery() {
        val query = savedStateHandle.get<String>("query")
        query?.let {
            _currentQuery.value = query
            setPendingScrollToTopAfterRefresh(true)
        }
        Timber.tag(tag).d("restore currentQuery = $query")
    }

    fun onFavoriteClick(wallpaper: Wallpaper) {
        val isFavorite = wallpaper.isFavorite
        wallpaper.isFavorite = !isFavorite
        onDispatcher(ioDispatcher) {
            repository.updateWallpaper(wallpaper)
        }
    }
}