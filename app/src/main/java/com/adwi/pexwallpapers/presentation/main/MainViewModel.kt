package com.adwi.pexwallpapers.presentation.main

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import com.adrianwitaszak.tool_image.ImageManager
import com.adwi.components.IoDispatcher
import com.adwi.components.base.BaseViewModel
import com.adwi.components.ext.onDispatcher
import com.adwi.data.database.dao.WallpapersDao
import com.adwi.data.database.domain.toDomain
import com.adwi.feature_settings.data.database.SettingsDao
import com.adwi.feature_settings.data.database.model.Settings
import com.adwi.pexwallpapers.domain.model.Wallpaper
import com.adwi.pexwallpapers.domain.util.shareImage
import com.adwi.tool_automation.AutomationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class MainViewModel @ExperimentalCoroutinesApi
@ExperimentalPagingApi
@Inject constructor(
    private val wallpapersDao: WallpapersDao,
    private val settingsDao: SettingsDao,
    private val imageManager: ImageManager,
    private val automationManager: AutomationManager,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _favorites: MutableStateFlow<List<Wallpaper>> = MutableStateFlow(listOf())
    private val _settings: MutableStateFlow<Settings> = MutableStateFlow(Settings())

    val settings = _settings.asStateFlow()

    init {
        getFavorites()
        getSettings()
    }

    private fun getSettings() {
        onDispatcher(ioDispatcher) {
            settingsDao.getSettings().collect {
                _settings.value = it
                lowRes = it.lowResMiniatures
                showShadows = it.showShadows
                showParallax = it.showParallax
            }
        }
    }

    fun contactSupport() {
        setSnackBar("contacted support")
    }

    fun aboutUs() {
        setSnackBar("about us")
    }

    fun saveAutomation() {
        Timber.tag(tag).d("saveAutomation")
        onDispatcher(ioDispatcher) {

            validateBeforeSaveAutomation(
                settings = settings.value,
                favorites = _favorites.value
            ) { list ->

                automationManager.startAutoChangeWallpaperWork(
                    delay = getTotalMinutesFromPeriods(
                        settings.value.minutes,
                        settings.value.hours,
                        settings.value.days
                    ),
                    favorites = list,
                )

                setSnackBar("Wallpaper will change in ${settings.value.hours} hours and ${settings.value.minutes} minutes")
                Timber.tag(tag).d("saveSettings - Delay = $settings.delay")
            }
        }
    }

    private fun validateBeforeSaveAutomation(
        settings: Settings,
        favorites: List<Wallpaper>,
        content: (List<Wallpaper>) -> Unit
    ) {
        if (favorites.size < 2) {
            automationManager.cancelAutoChangeWorks()
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
            wallpapersDao.getAllFavorites().collect { list ->
                _favorites.value = list.map { wallpaper -> wallpaper.toDomain() }
            }
        }
    }

    fun setWallpaper(
        imageUrl: String,
        setHomeScreen: Boolean,
        setLockScreen: Boolean
    ) {
        onDispatcher(ioDispatcher) {
            val bitmap = imageManager.getBitmapFromRemote(imageUrl)

            bitmap.data?.let {
                imageManager.setWallpaper(
                    bitmap = it,
                    home = setHomeScreen,
                    lock = setLockScreen
                ).message?.let { message ->
                    Timber.tag(tag).d(message)
                }
            }
        }
    }

    fun shareWallpaper(context: Context, wallpaper: Wallpaper) {
        onDispatcher(ioDispatcher) {
            // Fetch
            val bitmap = imageManager.getBitmapFromRemote(wallpaper.imageUrlPortrait)
            // Save
            bitmap.data?.let {
                val uri = imageManager.saveWallpaperLocally(wallpaper.id, it)
                uri?.let { uri2 ->
                    context.shareImage(uri2, wallpaper.photographer)
                }
            }
        }
    }

    fun downloadWallpaper(wallpaper: Wallpaper) {
        setSnackBar("Wallpaper downloaded")
        automationManager.createDownloadWallpaperWork(
            wallpaper,
            settings.value.downloadWallpapersOverWiFi
        )
    }

    fun cancelAutoChangeWorks() {
        automationManager.cancelAutoChangeWorks()
    }
}