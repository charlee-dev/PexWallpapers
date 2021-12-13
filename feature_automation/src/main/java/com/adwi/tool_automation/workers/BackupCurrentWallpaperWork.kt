package com.adwi.tool_automation.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker.Result.failure
import androidx.work.ListenableWorker.Result.success
import androidx.work.WorkerParameters
import com.adrianwitaszak.tool_image.ImageManager
import com.adwi.tool_automation.util.Constants.WALLPAPER_ID
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

private const val TAG = "AutoChangeWallpaperWork"

@HiltWorker
class BackupCurrentWallpaperWork @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val imageManager: ImageManager
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            // Get wallpaperId
            val wallpaperId = inputData.getInt(WALLPAPER_ID, 0)

            val bitmap = imageManager.getCurrentWallpaper(true)
            imageManager.saveWallpaperLocally(wallpaperId, bitmap)

            success()
        } catch (e: Exception) {
            Timber.tag(TAG).d(e.toString())
            failure()
        }
    }
}