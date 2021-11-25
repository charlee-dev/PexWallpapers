package com.adwi.settings

import androidx.paging.ExperimentalPagingApi
import com.adwi.core.IoDispatcher
import com.adwi.core.base.BaseViewModel
import com.adwi.core.util.ext.onDispatcher
import com.adwi.domain.Duration
import com.adwi.domain.Settings
import com.adwi.domain.Wallpaper
import com.adwi.repository.settings.SettingsRepositoryImpl
import com.adwi.repository.wallpaper.WallpaperRepositoryImpl
import com.adwi.shared.image.ImageTools
import com.adwi.shared.sharing.SharingTools
import com.adwi.shared.util.Constants.WORK_AUTO_WALLPAPER
import com.adwi.shared.work.WorkTools
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class SettingsViewModel
@Inject constructor(
    private val settingsRepository: SettingsRepositoryImpl,
    private val wallpaperRepository: WallpaperRepositoryImpl,
    private val workTools: WorkTools,
    private val sharingTools: SharingTools,
    private val imageTools: ImageTools,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val settings: MutableStateFlow<Settings> = MutableStateFlow(Settings())
    private val favorites: MutableStateFlow<List<Wallpaper>> = MutableStateFlow(emptyList())

    val days = MutableStateFlow(0)
    val hours = MutableStateFlow(0)
    val minutes = MutableStateFlow(1)

    init {
        getSettings()
        getFavorites()
    }

    private fun getSettings() {
        onDispatcher(ioDispatcher) {
            settingsRepository.getSettings().collect {
                settings.value = it
            }
        }
    }

    private fun getFavorites() {
        onDispatcher(ioDispatcher) {
            favorites.value = wallpaperRepository.getFavorites().first()
        }
    }

    fun updatePushNotifications(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsRepository.updatePushNotifications(checked) }
    }

    fun updateNewWallpaperSet(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsRepository.updateNewWallpaperSet(checked) }
    }

    fun updateWallpaperRecommendations(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsRepository.updateWallpaperRecommendations(checked) }
    }

    fun updateAutoChangeWallpaper(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsRepository.updateAutoChangeWallpaper(checked) }
    }

    fun updateChangeDurationSelected(durationSelected: Duration) {
        onDispatcher(ioDispatcher) {
            settingsRepository.updateChangeDurationSelected(
                durationSelected
            )
        }
    }

    fun updateChangeDurationValue(durationValue: Float) {
        onDispatcher(ioDispatcher) { settingsRepository.updateChangeDurationValue(durationValue) }
    }

    fun updateDownloadOverWiFi(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsRepository.updateDownloadOverWiFi(checked) }
    }

    fun updateAutoHome(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsRepository.updateAutoHome(checked) }
    }

    fun updateAutoLock(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsRepository.updateAutoLock(checked) }
    }

    fun resetSettings() {
        onDispatcher(ioDispatcher) { settingsRepository.resetAllSettings() }
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

    fun contactSupport() {
        sharingTools.contactSupport()
    }

    fun aboutUs() {
        setSnackBar("Not implemented yet")
    }

    fun privacyPolicy() {
        setSnackBar("Not implemented yet")
    }

    private fun cancelWorks(workTag: String) {
        workTools.cancelWorks(workTag)
        imageTools.deleteAllBackups()
    }

    fun saveAutomation() {
        onDispatcher(ioDispatcher) {
            if (settings.value.autoChangeWallpaper && favorites.value.isNotEmpty()) {
                val delay = getDelay(
                    days = days.value,
                    hours = hours.value,
                    minutes = minutes.value
                )
                workTools.setupAutoChangeWallpaperWorks(
                    favorites = favorites.value,
                    timeValue = delay
                )
                Timber.tag(TAG).d("saveSettings - Delay = $delay")
            } else {
                cancelWorks(WORK_AUTO_WALLPAPER)
            }
        }
    }

    private fun getDelay(days: Int, hours: Int, minutes: Int): Long {
        val hour = 60
        val day = 24 * hour.toLong()
        return (day * days) + (hour * hours) + minutes
    }
}

private const val TAG = "SettingsViewModel"