package com.adwi.pexwallpapers.ui.screens.main

import androidx.paging.ExperimentalPagingApi
import com.adwi.pexwallpapers.data.settings.SettingsDao
import com.adwi.pexwallpapers.data.wallpapers.repository.WallpaperRepositoryImpl
import com.adwi.pexwallpapers.di.IoDispatcher
import com.adwi.pexwallpapers.model.Wallpaper
import com.adwi.pexwallpapers.shared.image.ImageTools
import com.adwi.pexwallpapers.shared.setter.WallpaperSetter
import com.adwi.pexwallpapers.shared.sharing.SharingTools
import com.adwi.pexwallpapers.shared.work.WorkTools
import com.adwi.pexwallpapers.ui.base.BaseViewModel
import com.adwi.pexwallpapers.util.Constants.WORK_AUTO_WALLPAPER
import com.adwi.pexwallpapers.util.ext.onDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class MainViewModel @ExperimentalCoroutinesApi
@ExperimentalPagingApi
@Inject constructor(
    private val wallpaperRepository: WallpaperRepositoryImpl,
    private val settingsDao: SettingsDao,
    private val workTools: WorkTools,
    private val imageTools: ImageTools,
    private val sharingTools: SharingTools,
    private val wallpaperSetter: WallpaperSetter,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

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
                cancelWorks(WORK_AUTO_WALLPAPER)
            }
        }
    }

    private suspend fun getFavorites() = wallpaperRepository.getFavorites().first()

    private fun cancelWorks(workTag: String) {
        workTools.cancelWorks(workTag)
        imageTools.deleteAllBackups()
    }

    fun shareWallpaper(wallpaper: Wallpaper) {
        onDispatcher(ioDispatcher) {
            val uri = imageTools.fetchRemoteAndSaveLocally(wallpaper.id, wallpaper.imageUrlPortrait)
            uri?.let {
                sharingTools.shareImage(uri, wallpaper.photographer)
            }
        }
    }

    fun downloadWallpaper(wallpaper: Wallpaper) {
        onDispatcher(ioDispatcher) {
            val settings = settingsDao.getSettings().first()
            workTools.createDownloadWallpaperWork(wallpaper, settings.downloadOverWiFi)
        }
    }

    fun setWallpaper(imageUrl: String, setHomeScreen: Boolean, setLockScreen: Boolean) {
        onDispatcher(ioDispatcher) {
            val bitmap = imageTools.getBitmapFromRemote(imageUrl)

            bitmap?.let {
                wallpaperSetter.setWallpaper(
                    bitmap = bitmap,
                    setHomeScreen = setHomeScreen,
                    setLockScreen = setLockScreen
                )
            }
        }
    }
}