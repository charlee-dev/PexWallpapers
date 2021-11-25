package com.adwi.preview

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.SavedStateHandle
import androidx.paging.ExperimentalPagingApi
import com.adwi.core.IoDispatcher
import com.adwi.core.base.BaseViewModel
import com.adwi.core.util.ext.onDispatcher
import com.adwi.domain.Wallpaper
import com.adwi.repository.settings.SettingsRepositoryImpl
import com.adwi.repository.wallpaper.WallpaperRepositoryImpl
import com.adwi.shared.image.ImageTools
import com.adwi.shared.setter.WallpaperSetter
import com.adwi.shared.sharing.SharingTools
import com.adwi.shared.work.WorkTools
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
class PreviewViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val wallpaperRepository: WallpaperRepositoryImpl,
    private val settingsRepository: SettingsRepositoryImpl,
    private val imageTools: ImageTools,
    private val sharingTools: SharingTools,
    private val wallpaperSetter: WallpaperSetter,
    private val workTools: WorkTools,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val wallpaper: MutableStateFlow<Wallpaper?> = MutableStateFlow(null)

    init {
        savedStateHandle.get<Int>("wallpaperId")?.let { wallpaperId ->
            getWallpaperById(wallpaperId)
        }
    }

    private fun getWallpaperById(id: Int) {
        onDispatcher(ioDispatcher) {
            wallpaperRepository.getWallpaperById(id).collect { wallpaper.value = it }
        }
    }

    fun goToPexels(url: String) {
        sharingTools.openUrlInBrowser(url)
    }

    fun shareWallpaper(activity: AppCompatActivity, wallpaper: Wallpaper) {
        onDispatcher(ioDispatcher) {
            val uri = imageTools.fetchRemoteAndSaveLocally(wallpaper.id, wallpaper.imageUrlPortrait)
            uri?.let {
                sharingTools.shareImage(activity, uri, wallpaper.photographer)
            }
        }
    }

    fun downloadWallpaper(wallpaper: Wallpaper) {
        onDispatcher(ioDispatcher) {
            val settings = settingsRepository.getSettings().first()
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

    fun onFavoriteClick(wallpaper: Wallpaper) {
        val isFavorite = wallpaper.isFavorite
        wallpaper.isFavorite = !isFavorite
        onDispatcher(ioDispatcher) {
            wallpaperRepository.updateWallpaper(wallpaper)
        }
    }
}