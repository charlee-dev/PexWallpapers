package com.adwi.home

import androidx.paging.ExperimentalPagingApi
import com.adwi.common.IoDispatcher
import com.adwi.common.base.BaseViewModel
import com.adwi.common.util.ext.onDispatcher
import com.adwi.domain.Duration
import com.adwi.domain.Settings
import com.adwi.domain.Wallpaper
import com.adwi.repository.settings.SettingsRepositoryImpl
import com.adwi.repository.wallpaper.WallpaperRepositoryImpl
import com.adwi.settings.SettingsEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class SettingsViewModel
@Inject constructor(
    private val settingsRepository: SettingsRepositoryImpl,
    private val wallpaperRepository: WallpaperRepositoryImpl,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val settings: MutableStateFlow<Settings> = MutableStateFlow(Settings())
    private val favorites: MutableStateFlow<List<Wallpaper>> = MutableStateFlow(emptyList())

    init {
        onTriggerEvent(SettingsEvent.GetSettings)
        onTriggerEvent(SettingsEvent.GetFavorites)
    }

    fun onTriggerEvent(event: SettingsEvent) {
        when (event) {
            SettingsEvent.GetSettings -> getSettings()
            SettingsEvent.GetFavorites -> getFavorites()

            SettingsEvent.ContactSupport -> contactSupport()
            SettingsEvent.ResetSettings -> {
                resetSettings()
                getSettings()
            }

            is SettingsEvent.UpdateAutoChangeWallpaper -> {
                updateAutoChangeWallpaper(event.checked)
            }
            is SettingsEvent.UpdateNewWallpaperSet -> {
                updateNewWallpaperSet(event.checked)
            }
            is SettingsEvent.UpdateWallpaperRecommendations -> {
                updateWallpaperRecommendations(event.checked)
            }

            is SettingsEvent.UpdateAutoHome -> {
                updateAutoHome(event.checked)
            }
            is SettingsEvent.UpdateAutoLock -> {
                updateAutoLock(event.checked)
            }
            is SettingsEvent.UpdateChangePeriodType -> {
                updateChangePeriodType(event.durationSelected)
            }
            is SettingsEvent.UpdateChangePeriodValue -> {
                updateChangePeriodValue(event.durationValue)
            }

            is SettingsEvent.UpdateDownloadOverWiFi -> {
                updateDownloadOverWiFi(event.checked)
            }
        }
    }

    private fun getSettings() {
        onDispatcher(ioDispatcher) {
            settingsRepository.getSettings().collect { settings.value = it }
        }
    }

    private fun getFavorites() {
        onDispatcher(ioDispatcher) {
            favorites.value = wallpaperRepository.getFavorites().first()
        }
    }

    private fun updateNewWallpaperSet(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsRepository.updateNewWallpaperSet(checked) }
    }

    private fun updateWallpaperRecommendations(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsRepository.updateWallpaperRecommendations(checked) }
    }

    private fun updateAutoChangeWallpaper(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsRepository.updateAutoChangeWallpaper(checked) }
    }

    private fun updateChangePeriodType(durationSelected: Duration) {
        onDispatcher(ioDispatcher) { settingsRepository.updateChangePeriodType(durationSelected) }
    }

    private fun updateChangePeriodValue(durationValue: Float) {
        onDispatcher(ioDispatcher) { settingsRepository.updateChangePeriodValue(durationValue) }
    }

    private fun updateDownloadOverWiFi(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsRepository.updateDownloadOverWiFi(checked) }
    }

    private fun updateAutoHome(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsRepository.updateAutoHome(checked) }
    }

    private fun updateAutoLock(checked: Boolean) {
        onDispatcher(ioDispatcher) { settingsRepository.updateAutoLock(checked) }
    }

    private fun resetSettings() {
        onDispatcher(ioDispatcher) { settingsRepository.resetAllSettings() }
    }

    private fun contactSupport() {
//        sharingTools.contactSupport()
    }

//    fun cancelWorks(workTag: String) {
//        workTools.cancelWorks(workTag)
//        imageTools.deleteAllBackups()
//    }
//
//    fun saveSettings(settings: Settings) {
//        onDispatcher(ioDispatcher) {
//            if (settings.autoChangeWallpaper && favorites.value.isNotEmpty()) {
//                workTools.setupAutoChangeWallpaperWorks(
//                    favorites = favorites.value,
//                    timeUnit = getTimeUnit(settings.selectedButton),
//                    timeValue = settings.sliderValue
//                )
//            } else {
//                // Cancel works if not favorites list is empty
//                cancelWorks(WORK_AUTO_WALLPAPER)
//            }
//        }
//    }
//
//    private fun getTimeUnit(buttonId: Int): TimeUnit {
//        return when (buttonId) {
//            R.id.minutes_radio_button -> TimeUnit.MINUTES
//            R.id.hours_radio_button -> TimeUnit.HOURS
//            else -> TimeUnit.DAYS
//        }
//    }

}