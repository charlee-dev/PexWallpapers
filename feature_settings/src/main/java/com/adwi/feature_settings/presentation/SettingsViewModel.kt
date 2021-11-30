package com.adwi.feature_settings.presentation

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import com.adwi.components.IoDispatcher
import com.adwi.components.base.BaseViewModel
import com.adwi.components.ext.onDispatcher
import com.adwi.feature_settings.data.database.SettingsDao
import com.adwi.feature_settings.data.database.model.Settings
import com.adwi.pexwallpapers.domain.model.Wallpaper
import com.adwi.repository.WallpaperRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class SettingsViewModel
@Inject constructor(
    private val settingsDao: SettingsDao,
    private val wallpaperRepository: WallpaperRepositoryImpl,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {


    private val _settings: MutableStateFlow<Settings> = MutableStateFlow(Settings())
    private val _favorites: MutableStateFlow<List<Wallpaper>> = MutableStateFlow(listOf())
    //    private val _saveState: MutableStateFlow<Result> = MutableStateFlow(Result.Idle)

    val settings = _settings.asStateFlow()
    val favorites = _favorites.asStateFlow()
    //    val saveState = _saveState.asStateFlow()

    init {
        getFavorites()
    }

    fun getSettings() {
        onDispatcher(ioDispatcher) {
            settingsDao.getSettings().collect {
                _settings.value = it
            }
        }
    }

    // Notifications
    fun updatePushNotifications(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsDao.updatePushNotifications(checked) }
    }

    fun updateNewWallpaperSet(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsDao.updateNewWallpaperSet(checked) }
    }

    fun updateWallpaperRecommendations(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsDao.updateWallpaperRecommendations(checked) }
    }

    // Automation
    fun updateAutoChangeWallpaper(context: Context, checked: Boolean) {
        onDispatcher(ioDispatcher) {
            settingsDao.updateAutoChangeWallpaper(checked)
            if (!checked) cancelAutoChangeWorks(context)
        }
    }

    fun updateAutoHome(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsDao.updateAutoHome(checked) }
    }

    fun updateAutoLock(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsDao.updateAutoLock(checked) }
    }

    fun updateMinutes(value: Int) {
        onDispatcher(ioDispatcher) {
            settingsDao.updateMinutes(value)
        }
    }

    fun updateHours(value: Int) {
        onDispatcher(ioDispatcher) {
            settingsDao.updateHours(value)
        }
    }

    fun updateDays(value: Int) {
        onDispatcher(ioDispatcher) {
            settingsDao.updateDays(value)
        }
    }

    private fun getTotalMinutesFromPeriods(
        minutes: Int = settings.value.minutes,
        hours: Int = settings.value.hours,
        days: Int = settings.value.days
    ): Long {
        val hour = 60
        val day = 24 * hour

        return (day * days) + (hour * hours) + minutes.toLong()
    }

    // Data saver
    fun updateActivateDataSaver(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsDao.updateActivateDataSaver(checked) }
    }

    fun updateDownloadWallpapersOverWiFi(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsDao.updateDownloadWallpapersOverWiFi(checked) }
    }

    fun updateDownloadMiniaturesLowQuality(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsDao.updateDownloadMiniaturesLowQuality(checked) }
    }

    fun updateAutoChangeOverWiFi(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsDao.updateAutoChangeOverWiFi(checked) }
    }

    fun resetSettings() {
        onDispatcher(ioDispatcher) { settingsDao.insertSettings(Settings.default) }
    }

    fun saveAutomation(context: Context) {
        onDispatcher(ioDispatcher) {

//            context.validateBeforeSaveAutomation(
//                favorites = favorites.value
//            ) { list ->
//
//                val result = context.createAutoWork(
//                    delay = getTotalMinutesFromPeriods(),
//                    favorites = list,
//                )
//
//                when (result) {
//                    is Resource.Error -> {
//                        setSnackBar(result.message ?: "Some tasks failed")
//                        Timber.tag(tag).d(result.message ?: "Some tasks failed")
//                    }
//                    is Resource.Loading -> {
//                        Timber.tag(tag).d("saveAutomation - Loading = ${result.progress}")
//                    }
//                    is Resource.Success -> {
//                        Timber.tag(tag).d("saveAutomation - Success")
//                        setSnackBar("Automation saved")
//                    }
//                    else -> {
//                        // Idle
//                    }
//                }
//
//                setSnackBar("Wallpaper will change in ${settings.value.hours} hours and ${settings.value.minutes} minutes")
//                Timber.tag(tag).d("saveSettings - Delay = ${getTotalMinutesFromPeriods()}")
//            }
        }
    }

    private fun Context.validateBeforeSaveAutomation(
        favorites: List<Wallpaper>,
        content: (List<Wallpaper>) -> Unit
    ) {
        if (favorites.size < 2) {
            cancelAutoChangeWorks(this)
            setSnackBar("Add minimum two wallpapers to favorites")
        } else {
            if (!settings.value.autoHome && !settings.value.autoLock) {
                setSnackBar("Choose minimum one screen to change wallpaper")
            } else {
                content(favorites)
            }
        }
    }

    fun getFavorites() {
        onDispatcher(ioDispatcher) {
            wallpaperRepository.getFavorites().collect {
                _favorites.value = it
            }
        }
    }

    fun cancelAutoChangeWorks(context: Context) {
//        context.cancelAutoChangeWorks()
    }
}