package com.adwi.home

import androidx.paging.ExperimentalPagingApi
import com.adwi.core.IoDispatcher
import com.adwi.core.base.BaseViewModel
import com.adwi.core.base.Event
import com.adwi.core.base.Refresh
import com.adwi.core.util.CalendarUtil
import com.adwi.core.util.Constants.COULD_NOT_REFRESH
import com.adwi.core.util.Logger
import com.adwi.core.util.ext.exhaustive
import com.adwi.core.util.ext.onDispatcher
import com.adwi.domain.ColorCategory
import com.adwi.domain.Wallpaper
import com.adwi.interactors.settings.SettingsRepositoryImpl
import com.adwi.interactors.wallpaper.WallpaperRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val wallpaperRepository: WallpaperRepositoryImpl,
    private val settingsRepository: SettingsRepositoryImpl,
    private val logger: Logger,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val dailyWallpaper: MutableStateFlow<Wallpaper> = MutableStateFlow(Wallpaper())
    val colorList: MutableStateFlow<List<ColorCategory>> = MutableStateFlow(emptyList())
    val curatedList: MutableStateFlow<List<Wallpaper>> = MutableStateFlow(emptyList())

    private val eventChannel = Channel<Event>()
    private val events = eventChannel.receiveAsFlow()

    private val refreshTriggerChannel = Channel<Refresh>()
    val refreshTrigger = refreshTriggerChannel.receiveAsFlow()

    var pendingScrollToTopAfterRefresh = false

    init {
        onTriggerEvent(HomeEvent.Refresh)
        getEvents()
    }

    fun onTriggerEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.GetDaily -> {
                logger.log("onTriggerEvent - getDaily")
                getDaily()
            }
            HomeEvent.GetColors -> {
                logger.log("onTriggerEvent - GetColors")
                getColors()
            }
            HomeEvent.GetCurated -> {
                logger.log("onTriggerEvent - GetCurated")
                getCurated()
            }
            is HomeEvent.SetCategory -> {
                logger.log("onTriggerEvent - SetCategory")
                setCategory(event.categoryName)
            }
            HomeEvent.Refresh -> {
                onTriggerEvent(HomeEvent.GetDaily)
                onTriggerEvent(HomeEvent.GetColors)
                onTriggerEvent(HomeEvent.GetCurated)
            }
            is HomeEvent.OnFavoriteClick -> doFavorite(event.wallpaper)
        }
    }

    private fun getDaily(refresh: Boolean = true) {
        onDispatcher(ioDispatcher) {
            wallpaperRepository.getDaily(
                forceRefresh = refresh,
                onFetchSuccess = {},
                onFetchRemoteFailed = { setEventMessage(it) }
            ).collect {
                val list = it.data ?: return@collect
                Timber.tag("HomeViewModel").d("getDailyWallpaper ${list.size}")
                if (list.isNotEmpty()) {
                    dailyWallpaper.value = getTodayDaily(list)
                }
            }
        }
    }

    private fun getColors(refresh: Boolean = true) {
        onDispatcher(ioDispatcher) {
            wallpaperRepository.getColors(
                forceRefresh = refresh,
                onFetchSuccess = { },
                onFetchRemoteFailed = { setEventMessage(it) }
            ).collect { colorList.value = it.data ?: return@collect }
        }
    }

    private fun getCurated(refresh: Boolean = true) {
        onDispatcher(ioDispatcher) {
            wallpaperRepository.getCurated(
                forceRefresh = refresh,
                onFetchSuccess = { pendingScrollToTopAfterRefresh = true },
                onFetchRemoteFailed = { t ->
                    onDispatcher(ioDispatcher) { eventChannel.send(Event.ShowErrorMessage(t)) }
                }
            ).collect { curatedList.value = it.data ?: return@collect }
        }
    }

    private fun getTodayDaily(list: List<Wallpaper>): Wallpaper {
        val day: Int = CalendarUtil.getDayOfMonthNumber()
        return list[day]
    }

    private fun setCategory(categoryName: String) {
        onDispatcher(ioDispatcher) {
            settingsRepository.updateLastQuery(categoryName)
        }
    }

    private fun doFavorite(wallpaper: Wallpaper) {
        onDispatcher(ioDispatcher) {
            val isFavorite = wallpaper.isFavorite
            val newWallpaper = wallpaper.copy(isFavorite = !isFavorite)
            snackBarMessage.value = "Long pressed"
            wallpaperRepository.updateWallpaper(newWallpaper)
        }
    }

    private fun getEvents() {
        onDispatcher(ioDispatcher) {
            events.collect { event ->
                when (event) {
                    is Event.ShowErrorMessage -> {
                        setSnackBar(event.error.localizedMessage ?: COULD_NOT_REFRESH)
                    }
                }.exhaustive
            }
        }
    }

    private fun setEventMessage(t: Throwable) {
        onDispatcher(ioDispatcher) { eventChannel.send(Event.ShowErrorMessage(t)) }
    }
}
