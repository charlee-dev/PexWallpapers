package com.adwi.pexwallpapers.shared.setter

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import com.adwi.pexwallpapers.model.state.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

private const val TAG = "WallpaperSetter"

@SuppressLint("NewApi")
class WallpaperSetter @Inject constructor(
    @ApplicationContext private val context: Context,
    private val wallpaperManager: WallpaperManager
) {
    fun setWallpaper(bitmap: Bitmap, setHomeScreen: Boolean, setLockScreen: Boolean): Flow<Result> =
        flow {
            try {
                emit(Result.Loading)
                delay(1000)
                if (setHomeScreen) emit(setHomeScreenWallpaper(bitmap))
                if (setLockScreen) emit(setLockScreenWallpaper(bitmap))
            } catch (ex: IOException) {
                Timber.tag(TAG).d("Exception: ${ex.printStackTrace()}")
                emit(Result.Error(message = ex.localizedMessage))
            }
        }

    private fun setHomeScreenWallpaper(bitmap: Bitmap): Result = try {
        wallpaperManager.setBitmap(bitmap)
        Result.Success
    } catch (e: Exception) {
        Timber.tag(TAG).d(e.printStackTrace().toString())
        Result.Error(e.localizedMessage)
    }


    private fun setLockScreenWallpaper(bitmap: Bitmap): Result = try {
        wallpaperManager.setBitmap(
            bitmap, null, true, WallpaperManager.FLAG_LOCK
        )
        Result.Success
    } catch (e: Exception) {
        Timber.tag(TAG).d(e.printStackTrace().toString())
        Result.Error(e.localizedMessage)
    }

    @SuppressLint("MissingPermission")
    fun getCurrentWallpaperForBackup(): Bitmap =
        wallpaperManager
            .drawable
            .toBitmap()
}