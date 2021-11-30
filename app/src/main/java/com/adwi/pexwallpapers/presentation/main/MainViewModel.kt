package com.adwi.pexwallpapers.presentation.main

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import com.adwi.components.IoDispatcher
import com.adwi.components.base.BaseViewModel
import com.adwi.components.ext.onDispatcher
import com.adwi.core.Resource
import com.adwi.feature_settings.data.database.model.Settings
import com.adwi.pexwallpapers.domain.model.Wallpaper
import com.adwi.pexwallpapers.presentation.work.cancelAutoChangeWorks
import com.adwi.pexwallpapers.presentation.work.createAutoWork
import com.adwi.repository.WallpaperRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class MainViewModel @ExperimentalCoroutinesApi
@ExperimentalPagingApi
@Inject constructor(
    private val wallpaperRepository: WallpaperRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _favorites: MutableStateFlow<List<Wallpaper>> = MutableStateFlow(listOf())
    val favorites = _favorites.asStateFlow()

    init {
        getFavorites()
    }

    fun saveAutomation(context: Context, settings: Settings) {
        onDispatcher(ioDispatcher) {

            context.validateBeforeSaveAutomation(
                settings = settings,
                favorites = favorites.value
            ) { list ->

                val result = context.createAutoWork(
                    delay = getTotalMinutesFromPeriods(
                        settings.minutes,
                        settings.hours,
                        settings.days
                    ),
                    favorites = list,
                )

                when (result) {
                    is Resource.Error -> {
                        setSnackBar(result.message ?: "Some tasks failed")
                        Timber.tag(tag).d(result.message ?: "Some tasks failed")
                    }
                    is Resource.Loading -> {
                        Timber.tag(tag).d("saveAutomation - Loading = ${result.progress}")
                    }
                    is Resource.Success -> {
                        Timber.tag(tag).d("saveAutomation - Success")
                        setSnackBar("Automation saved")
                    }
                    else -> {
                        // Idle
                    }
                }

                setSnackBar("Wallpaper will change in $settings.hours hours and $settings.minutes minutes")
                Timber.tag(tag).d("saveSettings - Delay = $settings.delay")
            }
        }
    }

    private fun Context.validateBeforeSaveAutomation(
        settings: Settings,
        favorites: List<Wallpaper>,
        content: (List<Wallpaper>) -> Unit
    ) {
        if (favorites.size < 2) {
            cancelAutoChangeWorks()
            setSnackBar("Add minimum two wallpapers to favorites")
        } else {
            if (!settings.autoHome && !settings.autoLock) {
                setSnackBar("Choose minimum one screen to change wallpaper")
            } else {
                content(favorites)
            }
        }
    }

    private fun getTotalMinutesFromPeriods(
        minutes: Int,
        hours: Int,
        days: Int
    ): Long {
        val hour = 60
        val day = 24 * hour

        return (day * days) + (hour * hours) + minutes.toLong()
    }

    private fun getFavorites() {
        onDispatcher(ioDispatcher) {
            wallpaperRepository.getFavorites().collect {
                _favorites.value = it
            }
        }
    }
}