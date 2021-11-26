package com.adwi.pexwallpapers.ui.screens.settings

import androidx.paging.ExperimentalPagingApi
import com.adwi.datasource_settings.domain.Duration
import com.adwi.datasource_settings.domain.Settings
import com.adwi.pexwallpapers.data.settings.SettingsDao
import com.adwi.pexwallpapers.data.settings.model.toDomain
import com.adwi.pexwallpapers.data.settings.model.toEntity
import com.adwi.pexwallpapers.data.wallpapers.repository.WallpaperRepositoryImpl
import com.adwi.pexwallpapers.di.IoDispatcher
import com.adwi.pexwallpapers.model.state.Result
import com.adwi.pexwallpapers.shared.image.ImageTools
import com.adwi.pexwallpapers.shared.work.WorkTools
import com.adwi.pexwallpapers.ui.base.BaseViewModel
import com.adwi.pexwallpapers.util.Constants
import com.adwi.pexwallpapers.util.ext.onDispatcher
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
    private val workTools: WorkTools,
    private val imageTools: ImageTools,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _settings: MutableStateFlow<Settings> = MutableStateFlow(Settings())
    private val _saveState: MutableStateFlow<Result> = MutableStateFlow(Result.Idle)
    private val _days = MutableStateFlow(0)
    private val _hours = MutableStateFlow(0)
    private val _minutes = MutableStateFlow(1)

    val settings = _settings.asStateFlow()
    val saveState = _saveState.asStateFlow()
    val days = _days.asStateFlow()
    val hours = _hours.asStateFlow()
    val minutes = _minutes.asStateFlow()

    init {
        getSettings()
    }

    private fun getSettings() {
        onDispatcher(ioDispatcher) {
            settingsDao.getSettings().collect {
                _settings.value = it.toDomain()
//                formatDelayTimeInMilliSeconds(it.durationValue)
            }
        }
    }

    fun updatePushNotifications(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsDao.updatePushNotifications(checked) }
    }

    fun updateNewWallpaperSet(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsDao.updateNewWallpaperSet(checked) }
    }

    fun updateWallpaperRecommendations(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsDao.updateWallpaperRecommendations(checked) }
    }

    fun updateAutoChangeWallpaper(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsDao.updateAutoChangeWallpaper(checked) }
    }

    fun updateChangeDurationSelected(durationSelected: Duration) {
        onDispatcher(ioDispatcher) {
            settingsDao.updateChangeDurationSelected(durationSelected)
        }
    }

    fun updateChangeDurationValue(durationValue: Float) {
        onDispatcher(ioDispatcher) { settingsDao.updateChangeDurationValue(durationValue) }
    }

    fun updateDownloadOverWiFi(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsDao.updateDownloadOverWiFi(checked) }
    }

    fun updateAutoHome(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsDao.updateAutoHome(checked) }
    }

    fun updateAutoLock(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsDao.updateAutoLock(checked) }
    }

    fun resetSettings() {
        onDispatcher(ioDispatcher) { settingsDao.insertSettings(Settings.default.toEntity()) }
    }

    fun setDays(value: Int) {
        onDispatcher(ioDispatcher) {
            _days.value = value
        }
    }

    fun setHours(value: Int) {
        onDispatcher(ioDispatcher) {
            _hours.value = value
        }
    }

    fun setMinutes(value: Int) {
        onDispatcher(ioDispatcher) {
            _minutes.value = value
        }
    }

    fun getDelay(): Long {

        val days = days.value
        val hours = hours.value
        val minutes = minutes.value

        val hour = 60
        val day = 24 * hour.toLong()

        return (day * days) + (hour * hours) + minutes
    }

    fun formatDelayTimeInMilliSeconds(milliSeconds: Long) {
        val days = TimeUnit.MILLISECONDS.toDays(milliSeconds).toInt()
        val hours = TimeUnit.MILLISECONDS.toHours(milliSeconds).toInt() % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliSeconds).toInt() % 60
        setDays(days)
        setHours(hours)
        setMinutes(minutes)
        Timber.tag(tag).d(
            "formatDelayTimeInMilliSeconds \nDays = $days \nHours = $hours \nMinutes = $minutes"
        )
    }

    fun saveAutomation(delay: Long) {
        onDispatcher(ioDispatcher) {

            val favorites = getFavorites()

            if (favorites.isNotEmpty()) {
                workTools.setupAutoChangeWallpaperWorks(
                    favorites = favorites,
                    timeValue = delay
                )
                Timber.tag(tag).d("saveSettings - Delay = $delay")
            } else {
                cancelWorks(Constants.WORK_AUTO_WALLPAPER)
            }
        }
    }

    private suspend fun getFavorites() = wallpaperRepository.getFavorites().first()

    private fun cancelWorks(workTag: String) {
        workTools.cancelWorks(workTag)
        imageTools.deleteAllBackups()
    }
}