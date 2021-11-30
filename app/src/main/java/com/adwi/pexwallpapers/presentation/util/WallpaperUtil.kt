package com.adwi.pexwallpapers.presentation.util

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.IOException

fun Context.setAsWallpaper(
    bitmap: Bitmap,
    setHomeScreen: Boolean,
    setLockScreen: Boolean
): Flow<com.adwi.core.Resource> =
    flow {
        try {
            val wallpaperManager = WallpaperManager.getInstance(this@setAsWallpaper)
            emit(com.adwi.core.Resource.Loading())
            delay(1000)
            if (setHomeScreen) emit(wallpaperManager.setHomeScreenWallpaper(bitmap))
            if (setLockScreen) emit(wallpaperManager.setLockScreenWallpaper(bitmap))
        } catch (ex: IOException) {
            Timber.tag(TAG)
                .d("Exception: ${ex.printStackTrace()}")
            emit(com.adwi.core.Resource.Error(message = ex.localizedMessage))
        }
    }

fun Context.setBitmapAsWallpaper(
    bitmap: Bitmap,
    setHomeScreen: Boolean,
    setLockScreen: Boolean
) {
    val wallpaperManager = WallpaperManager.getInstance(this)
    if (setHomeScreen) wallpaperManager.setHomeScreenWallpaper(bitmap)
    if (setLockScreen) wallpaperManager.setLockScreenWallpaper(bitmap)
}


private fun WallpaperManager.setHomeScreenWallpaper(bitmap: Bitmap): com.adwi.core.Resource =
    try {
        this.setBitmap(bitmap)
        com.adwi.core.Resource.Success()
    } catch (e: Exception) {
        Timber.tag(TAG).d(e.printStackTrace().toString())
        com.adwi.core.Resource.Error(e.localizedMessage)
    }


private fun WallpaperManager.setLockScreenWallpaper(bitmap: Bitmap): com.adwi.core.Resource =
    try {
        this.setBitmap(
            bitmap, null, true, WallpaperManager.FLAG_LOCK
        )
        com.adwi.core.Resource.Success()
    } catch (e: Exception) {
        Timber.tag(TAG).d(e.printStackTrace().toString())
        com.adwi.core.Resource.Error(e.localizedMessage)
    }

@SuppressLint("MissingPermission")

fun Context.getCurrentWallpaperForBackup(): com.adwi.core.DataState<Bitmap> {
    return try {
        val wallpaperManager = WallpaperManager.getInstance(this)
        val currentWallpaper = wallpaperManager
            .drawable
            .toBitmap()

        com.adwi.core.DataState.Success(currentWallpaper)
    } catch (t: Throwable) {
        com.adwi.core.DataState.Error(t)
    }
}

private const val TAG = "WallpaperUtil"