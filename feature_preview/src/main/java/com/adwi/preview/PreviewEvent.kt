package com.adwi.preview

import androidx.appcompat.app.AppCompatActivity
import com.adwi.domain.Wallpaper

sealed class PreviewEvent {

    data class GetWallpaperById(val wallpaperId: Int) : PreviewEvent()

    data class GoToPexels(val wallpaper: Wallpaper) : PreviewEvent()

    data class ShareWallpaper(
        val activity: AppCompatActivity,
        val wallpaper: Wallpaper
    ) : PreviewEvent()

    data class DownloadWallpaper(val wallpaper: Wallpaper) : PreviewEvent()

    data class SetWallpaper(
        val imageUrl: String,
        val setHomeScreen: Boolean,
        val setLockScreen: Boolean
    ) : PreviewEvent()

    data class DoFavorite(val wallpaper: Wallpaper) : PreviewEvent()

    data class ShowSnackbar(val message: String) : PreviewEvent()
}
