package com.adwi.favorites

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.adwi.core.IoDispatcher
import com.adwi.core.base.BaseViewModel
import com.adwi.core.domain.DataState
import com.adwi.core.util.Logger
import com.adwi.core.util.ext.onDispatcher
import com.adwi.domain.Wallpaper
import com.adwi.interactors.wallpaper.usecases.GetFavorites
import com.adwi.interactors.wallpaper.usecases.GetWallpaper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavorites: GetFavorites,
    private val getWallpaper: GetWallpaper,
    private val logger: Logger,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseViewModel() {

    var state: MutableState<FavoritesState> = mutableStateOf(FavoritesState())

    fun onTriggerEvent(event: FavoritesEvent) {
        when (event) {
            FavoritesEvent.GetFavorites -> getFavorites()

            is FavoritesEvent.OnFavoriteClick -> {
                doFavorite(event.wallpaper)
                getFavorites
            }
        }
    }

    private fun getFavorites() {
        getFavorites.execute().onEach { resource ->
            state.value.favorites.apply {
                when (resource) {
                    is DataState.Loading -> {
                        value = value.copy(loadingState = resource.loadingState)
                    }
                    is DataState.Data -> {
                        value = value.copy(
                            wallpapers = resource.data ?: listOf()
                        )
                    }
                    is DataState.Response -> {
                        logger.log(resource.error?.localizedMessage ?: "Resource - error")
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun doFavorite(wallpaper: Wallpaper) {
        onDispatcher(dispatcher) {
            val isFavorite = wallpaper.isFavorite
            val newWallpaper = wallpaper.copy(isFavorite = !isFavorite)
            snackBarMessage.value = "Long pressed"
            getWallpaper.update(newWallpaper)
        }
    }
}