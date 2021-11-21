package com.adrianwitaszak.wallpaper_setter

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import com.adwi.core.util.ext.showToast
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject


private const val TAG = "WallpaperSetter"

/**
 * Wallpaper setter
 *
 * @property context
 * @property wallpaperManager
 * @constructor Create empty Wallpaper setter
 */
@SuppressLint("NewApi")
class WallpaperSetter @Inject constructor(
    @ApplicationContext private val context: Context,
    private val wallpaperManager: WallpaperManager
) {
    /**
     * Set wallpaper
     *
     * @param bitmap
     * @param setHomeScreen
     * @param setLockScreen
     */
    fun setWallpaper(bitmap: Bitmap, setHomeScreen: Boolean, setLockScreen: Boolean) {
        try {
            if (setHomeScreen && !setLockScreen) setHomeScreenWallpaper(bitmap)
            if (!setHomeScreen && setLockScreen) setLockScreenWallpaper(bitmap)
            if (setHomeScreen && setLockScreen) {
                setHomeScreenWallpaper(bitmap)
                setLockScreenWallpaper(bitmap)
            }
        } catch (ex: IOException) {
            Timber.tag(TAG).d("Exception: ${ex.printStackTrace()}")
        }
    }

    /**
     * Set home screen wallpaper
     *
     * @param bitmap
     */
    private fun setHomeScreenWallpaper(
        bitmap: Bitmap
    ) {
        wallpaperManager.setBitmap(bitmap)
    }

    /**
     * Set lock screen wallpaper
     *
     * @param bitmap
     */
    private fun setLockScreenWallpaper(bitmap: Bitmap) {
        try {
            wallpaperManager.setBitmap(
                bitmap, null, true,
                WallpaperManager.FLAG_LOCK
            )
        } catch (e: Exception) {
            e.message?.let { showToast(context, it) }
        }
    }


    @SuppressLint("MissingPermission")
    fun getCurrentWallpaperForBackup() =
        wallpaperManager
            .drawable
            .toBitmap()
}