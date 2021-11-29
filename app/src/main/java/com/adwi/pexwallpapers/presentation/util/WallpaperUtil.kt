package com.adwi.pexwallpapers.presentation.util

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import com.adwi.pexwallpapers.domain.state.DataState
import com.adwi.pexwallpapers.domain.state.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.IOException

fun Context.setAsWallpaper(
    bitmap: Bitmap,
    setHomeScreen: Boolean,
    setLockScreen: Boolean
): Flow<Resource> =
    flow {
        try {
            val wallpaperManager = WallpaperManager.getInstance(this@setAsWallpaper)
            emit(Resource.Loading())
            delay(1000)
            if (setHomeScreen) emit(wallpaperManager.setHomeScreenWallpaper(bitmap))
            if (setLockScreen) emit(wallpaperManager.setLockScreenWallpaper(bitmap))
        } catch (ex: IOException) {
            Timber.tag(TAG)
                .d("Exception: ${ex.printStackTrace()}")
            emit(Resource.Error(message = ex.localizedMessage))
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


private fun WallpaperManager.setHomeScreenWallpaper(bitmap: Bitmap): Resource =
    try {
        this.setBitmap(bitmap)
        Resource.Success()
    } catch (e: Exception) {
        Timber.tag(TAG).d(e.printStackTrace().toString())
        Resource.Error(e.localizedMessage)
    }


private fun WallpaperManager.setLockScreenWallpaper(bitmap: Bitmap): Resource =
    try {
        this.setBitmap(
            bitmap, null, true, WallpaperManager.FLAG_LOCK
        )
        Resource.Success()
    } catch (e: Exception) {
        Timber.tag(TAG).d(e.printStackTrace().toString())
        Resource.Error(e.localizedMessage)
    }

@SuppressLint("MissingPermission")

fun Context.getCurrentWallpaperForBackup(): DataState<Bitmap> {
    return try {
        val wallpaperManager = WallpaperManager.getInstance(this)
        val currentWallpaper = wallpaperManager
            .drawable
            .toBitmap()

        DataState.Success(currentWallpaper)
    } catch (t: Throwable) {
        DataState.Error(t)
    }
}

private const val TAG = "WallpaperUtil"