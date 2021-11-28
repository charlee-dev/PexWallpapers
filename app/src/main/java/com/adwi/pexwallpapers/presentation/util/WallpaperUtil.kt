package com.adwi.pexwallpapers.presentation.util

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import com.adwi.pexwallpapers.domain.state.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.IOException

fun Context.setAsWallpaper(
    bitmap: Bitmap,
    setHomeScreen: Boolean,
    setLockScreen: Boolean
): Flow<Result> =
    flow {
        try {
            val wallpaperManager = WallpaperManager.getInstance(this@setAsWallpaper)
            emit(Result.Loading)
            delay(1000)
            if (setHomeScreen) emit(wallpaperManager.setHomeScreenWallpaper(bitmap))
            if (setLockScreen) emit(wallpaperManager.setLockScreenWallpaper(bitmap))
        } catch (ex: IOException) {
            Timber.tag(TAG)
                .d("Exception: ${ex.printStackTrace()}")
            emit(Result.Error(message = ex.localizedMessage))
        }
    }

private fun WallpaperManager.setHomeScreenWallpaper(bitmap: Bitmap): Result =
    try {
        this.setBitmap(bitmap)
        Result.Success
    } catch (e: Exception) {
        Timber.tag(TAG).d(e.printStackTrace().toString())
        Result.Error(e.localizedMessage)
    }


private fun WallpaperManager.setLockScreenWallpaper(bitmap: Bitmap): Result =
    try {
        this.setBitmap(
            bitmap, null, true, WallpaperManager.FLAG_LOCK
        )
        Result.Success
    } catch (e: Exception) {
        Timber.tag(TAG).d(e.printStackTrace().toString())
        Result.Error(e.localizedMessage)
    }

@SuppressLint("MissingPermission")

fun Context.getCurrentWallpaperForBackup(): Bitmap {
    val wallpaperManager = WallpaperManager.getInstance(this)

    return wallpaperManager
        .drawable
        .toBitmap()
}

private const val TAG = "WallpaperUtil"