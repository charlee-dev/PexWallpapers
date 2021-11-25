package com.adwi.pexwallpapers.shared.setter

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

private const val TAG = "WallpaperSetter"

@SuppressLint("NewApi")
class WallpaperSetter @Inject constructor(
    @ApplicationContext private val context: Context,
    private val wallpaperManager: WallpaperManager
) {
    fun setWallpaper(bitmap: Bitmap, setHomeScreen: Boolean, setLockScreen: Boolean) {
        try {
            if (setHomeScreen) setHomeScreenWallpaper(bitmap)
            if (setLockScreen) setLockScreenWallpaper(bitmap)
        } catch (ex: IOException) {
            Timber.tag(TAG).d("Exception: ${ex.printStackTrace()}")
        }
    }

    private fun setHomeScreenWallpaper(
        bitmap: Bitmap
    ) {
        wallpaperManager.setBitmap(bitmap)
    }

    private fun setLockScreenWallpaper(bitmap: Bitmap) {
        try {
            wallpaperManager.setBitmap(
                bitmap, null, true, WallpaperManager.FLAG_LOCK
            )
        } catch (e: Exception) {
            e.message?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentWallpaperForBackup(): Bitmap =
        wallpaperManager
            .drawable
            .toBitmap()
}