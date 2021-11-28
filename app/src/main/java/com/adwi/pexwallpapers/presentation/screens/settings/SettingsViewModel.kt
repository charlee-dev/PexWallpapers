package com.adwi.pexwallpapers.presentation.screens.settings

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import com.adwi.pexwallpapers.data.WallpaperRepositoryImpl
import com.adwi.pexwallpapers.data.database.settings.SettingsDao
import com.adwi.pexwallpapers.data.database.settings.model.Settings
import com.adwi.pexwallpapers.domain.model.Wallpaper
import com.adwi.pexwallpapers.presentation.IoDispatcher
import com.adwi.pexwallpapers.presentation.base.BaseViewModel
import com.adwi.pexwallpapers.presentation.util.Constants
import com.adwi.pexwallpapers.presentation.util.deleteAllBackups
import com.adwi.pexwallpapers.presentation.util.ext.onDispatcher
import com.adwi.pexwallpapers.presentation.work.workCancelWorks
import com.adwi.pexwallpapers.presentation.work.workSetupAutoChangeWallpaperWorks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import timber.log.Timber
import java.util.concurrent.TimeUnit
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

    //    private val _saveState: MutableStateFlow<Result> = MutableStateFlow(Result.Idle)
    private val _days = MutableStateFlow(0)
    private val _hours = MutableStateFlow(0)
    private val _minutes = MutableStateFlow(1)

    val settings = _settings.asStateFlow()

    //    val saveState = _saveState.asStateFlow()
    val days = _days.asStateFlow()
    val hours = _hours.asStateFlow()
    val minutes = _minutes.asStateFlow()

    fun getSettings() {
        onDispatcher(ioDispatcher) {
            settingsDao.getSettings().collect {
                _settings.value = it
                setDelayFromMillis(it.duration.toLong())
            }
        }
    }

    private fun setDelayFromMillis(milliSeconds: Long) {

        _minutes.value = TimeUnit.MILLISECONDS.toMinutes(milliSeconds).toInt() % 60
        _hours.value = TimeUnit.MILLISECONDS.toHours(milliSeconds).toInt() % 24
        _days.value = TimeUnit.MILLISECONDS.toDays(milliSeconds).toInt()

        Timber.tag(tag).d(
            "formatDelayTimeInMilliSeconds \nDays = ${days.value} \nHours = ${hours.value} \nMinutes = ${minutes.value}"
        )
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
    fun updateAutoChangeWallpaper(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsDao.updateAutoChangeWallpaper(checked) }
    }

    fun updateAutoHome(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsDao.updateAutoHome(checked) }
    }

    fun updateAutoLock(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsDao.updateAutoLock(checked) }
    }

    fun updateDuration(
        m: Int = minutes.value,
        h: Int = hours.value,
        d: Int = days.value
    ) {
        onDispatcher(ioDispatcher) {
            val duration = getDelay(m, h, d)
            settingsDao.updateDuration(duration)
        }
    }

    private fun getDelay(
        minutes: Int,
        hours: Int,
        days: Int
    ): Int {
        val hour = 60
        val day = 24 * hour

        return (day * days) + (hour * hours) + minutes
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

            val favorites = getFavorites()

            if (favorites.isNotEmpty()) {
                settings.value.let {
                    context.workSetupAutoChangeWallpaperWorks(
                        favorites = favorites,
                        timeValue = it.duration.toLong()
                    )
                    setSnackBar("Wallpaper will change in ${hours.value} hours and ${minutes.value} minutes")
                    Timber.tag(tag).d("saveSettings - Delay = ${settings.value.duration}")
                }
            } else {
                cancelWorks(context, Constants.WORK_AUTO_WALLPAPER)
            }
        }
    }

    private suspend fun getFavorites(): List<Wallpaper> = wallpaperRepository.getFavorites().first()

    private fun cancelWorks(context: Context, workTag: String) {
        context.workCancelWorks(workTag)
        context.deleteAllBackups()
    }
}