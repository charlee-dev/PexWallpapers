package com.adwi.preview

import androidx.appcompat.app.AppCompatActivity
import com.adwi.core.domain.Event
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

    data class OnFavoriteClick(val wallpaper: Wallpaper) : PreviewEvent()

    data class ShowMessageEvent(val event: Event) : PreviewEvent()
}
