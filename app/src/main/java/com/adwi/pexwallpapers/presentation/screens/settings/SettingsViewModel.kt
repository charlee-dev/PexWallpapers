package com.adwi.pexwallpapers.presentation.screens.settings

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import com.adwi.pexwallpapers.data.WallpaperRepositoryImpl
import com.adwi.pexwallpapers.data.database.settings.SettingsDao
import com.adwi.pexwallpapers.data.database.settings.model.toDomain
import com.adwi.pexwallpapers.data.database.settings.model.toEntity
import com.adwi.pexwallpapers.domain.model.Duration
import com.adwi.pexwallpapers.domain.model.Settings
import com.adwi.pexwallpapers.domain.state.Result
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

    private fun getDelay(): Long {

        val days = days.value
        val hours = hours.value
        val minutes = minutes.value

        val hour = 60
        val day = 24 * hour.toLong()

        return (day * days) + (hour * hours) + minutes
    }

//    fun formatDelayTimeInMilliSeconds(milliSeconds: Long) {
//        val days = TimeUnit.MILLISECONDS.toDays(milliSeconds).toInt()
//        val hours = TimeUnit.MILLISECONDS.toHours(milliSeconds).toInt() % 24
//        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliSeconds).toInt() % 60
//        setDays(days)
//        setHours(hours)
//        setMinutes(minutes)
//        Timber.tag(tag).d(
//            "formatDelayTimeInMilliSeconds \nDays = $days \nHours = $hours \nMinutes = $minutes"
//        )
//    }

    fun saveAutomation(context: Context) {
        onDispatcher(ioDispatcher) {

            val favorites = getFavorites()

            if (favorites.isNotEmpty()) {
                context.workSetupAutoChangeWallpaperWorks(
                    favorites = favorites,
                    timeValue = getDelay()
                )
                setSnackBar("Wallpaper will change in ${hours.value} hours and ${minutes.value} minutes")
                Timber.tag(tag).d("saveSettings - Delay = ${getDelay()}")
            } else {
                cancelWorks(context, Constants.WORK_AUTO_WALLPAPER)
            }
        }
    }

    private suspend fun getFavorites() = wallpaperRepository.getFavorites().first()

    private fun cancelWorks(context: Context, workTag: String) {
        context.workCancelWorks(workTag)
        context.deleteAllBackups()
    }
}