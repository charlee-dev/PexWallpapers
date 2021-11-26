package com.adwi.pexwallpapers.ui.screens.settings

import androidx.paging.ExperimentalPagingApi
import com.adwi.datasource_settings.domain.Duration
import com.adwi.datasource_settings.domain.Settings
import com.adwi.pexwallpapers.data.settings.SettingsDao
import com.adwi.pexwallpapers.data.settings.model.toDomain
import com.adwi.pexwallpapers.data.settings.model.toEntity
import com.adwi.pexwallpapers.di.IoDispatcher
import com.adwi.pexwallpapers.ui.base.BaseViewModel
import com.adwi.pexwallpapers.util.ext.onDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class SettingsViewModel
@Inject constructor(
    private val settingsDao: SettingsDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val settings: MutableStateFlow<Settings> = MutableStateFlow(Settings())

    val days = MutableStateFlow(0)
    val hours = MutableStateFlow(0)
    val minutes = MutableStateFlow(1)

    init {
        getSettings()
    }

    private fun getSettings() {
        onDispatcher(ioDispatcher) {
            settingsDao.getSettings().collect {
                settings.value = it.toDomain()
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
            days.value = value
        }
    }

    fun setHours(value: Int) {
        onDispatcher(ioDispatcher) {
            hours.value = value
        }
    }

    fun setMinutes(value: Int) {
        onDispatcher(ioDispatcher) {
            minutes.value = value
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
}