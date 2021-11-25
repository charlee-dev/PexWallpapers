package com.adwi.pexwallpapers.ui.screens.settings

import androidx.paging.ExperimentalPagingApi
import com.adwi.datasource_settings.domain.Duration
import com.adwi.datasource_settings.domain.Settings
import com.adwi.pexwallpapers.data.settings.SettingsDao
import com.adwi.pexwallpapers.data.settings.model.toDomain
import com.adwi.pexwallpapers.data.settings.model.toEntity
import com.adwi.pexwallpapers.data.wallpapers.repository.WallpaperRepositoryImpl
import com.adwi.pexwallpapers.di.IoDispatcher
import com.adwi.pexwallpapers.model.Wallpaper
import com.adwi.pexwallpapers.shared.image.ImageTools
import com.adwi.pexwallpapers.shared.sharing.SharingTools
import com.adwi.pexwallpapers.shared.work.WorkTools
import com.adwi.pexwallpapers.ui.base.BaseViewModel
import com.adwi.pexwallpapers.util.Constants.WORK_AUTO_WALLPAPER
import com.adwi.pexwallpapers.util.ext.onDispatcher
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
    private val settingsDao: SettingsDao,
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
            settingsDao.getSettings().collect {
                settings.value = it.toDomain()
            }
        }
    }

    private fun getFavorites() {
        onDispatcher(ioDispatcher) {
            favorites.value = wallpaperRepository.getFavorites().first()
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