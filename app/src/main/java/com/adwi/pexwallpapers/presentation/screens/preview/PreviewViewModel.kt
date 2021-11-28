package com.adwi.pexwallpapers.presentation.screens.preview

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.paging.ExperimentalPagingApi
import com.adwi.pexwallpapers.data.WallpaperRepositoryImpl
import com.adwi.pexwallpapers.data.database.settings.SettingsDao
import com.adwi.pexwallpapers.domain.model.Wallpaper
import com.adwi.pexwallpapers.domain.state.Result
import com.adwi.pexwallpapers.presentation.IoDispatcher
import com.adwi.pexwallpapers.presentation.base.BaseViewModel
import com.adwi.pexwallpapers.presentation.util.ext.onDispatcher
import com.adwi.pexwallpapers.presentation.util.fetchRemoteAndSaveLocally
import com.adwi.pexwallpapers.presentation.util.getBitmapFromRemote
import com.adwi.pexwallpapers.presentation.util.setAsWallpaper
import com.adwi.pexwallpapers.presentation.util.shareImage
import com.adwi.pexwallpapers.presentation.work.workCreateDownloadWallpaperWork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class PreviewViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val wallpaperRepository: WallpaperRepositoryImpl,
    private val settingsDao: SettingsDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _wallpaper: MutableStateFlow<Wallpaper?> = MutableStateFlow(null)
    private val _saveState: MutableStateFlow<Result> = MutableStateFlow(Result.Idle)

    val wallpaper = _wallpaper.asStateFlow()
    val saveState = _saveState.asStateFlow()


    init {
        savedStateHandle.get<Int>("wallpaperId")?.let { wallpaperId ->
            getWallpaperById(wallpaperId)
        }
    }

    private fun getWallpaperById(id: Int) {
        onDispatcher(ioDispatcher) {
            wallpaperRepository.getWallpaperById(id).collect { _wallpaper.value = it }
        }
    }

    fun onFavoriteClick(wallpaper: Wallpaper) {
        onDispatcher(ioDispatcher) {
            wallpaperRepository.updateWallpaperIsFavorite(wallpaper.id, !wallpaper.isFavorite)
            Timber.d("${!wallpaper.isFavorite}")
        }
    }

    fun setWallpaper(
        context: Context,
        imageUrl: String,
        setHomeScreen: Boolean,
        setLockScreen: Boolean
    ) {
        onDispatcher(ioDispatcher) {
            val bitmap = context.getBitmapFromRemote(imageUrl)

            bitmap?.let {
                context.setAsWallpaper(
                    bitmap = bitmap,
                    setHomeScreen = setHomeScreen,
                    setLockScreen = setLockScreen
                ).collect { result ->
                    _saveState.value = result
                    if (result is Result.Success || result is Result.Error) {
                        delay(2000)
                        _saveState.value = Result.Idle
                    }
                }
            }
        }
    }

    fun shareWallpaper(context: Context, wallpaper: Wallpaper) {
        onDispatcher(ioDispatcher) {
            val uri = context.fetchRemoteAndSaveLocally(wallpaper.id, wallpaper.imageUrlPortrait)
            uri?.let {
                context.shareImage(uri, wallpaper.photographer)
            }
        }
    }

    fun downloadWallpaper(context: Context, wallpaper: Wallpaper) {
        onDispatcher(ioDispatcher) {
            val settings = settingsDao.getSettings().first()
            context.workCreateDownloadWallpaperWork(wallpaper, settings.downloadOverWiFi)
        }
    }
}