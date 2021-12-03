package com.adwi.tool_automation.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker.Result.failure
import androidx.work.ListenableWorker.Result.success
import androidx.work.WorkerParameters
import com.adrianwitaszak.tool_image.ImageManager
import com.adwi.tool_automation.util.Constants.WALLPAPER_ID
import com.adwi.tool_automation.util.Constants.WALLPAPER_IMAGE_URL
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

private const val TAG = "DownloadWallpaperWork"

@HiltWorker
class DownloadAndSaveWallpaperWork @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val imageManager: ImageManager
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {

            // Get arguments
            val wallpaperId = inputData.getInt(WALLPAPER_ID, 0)
            val wallpaperImageUrl = inputData.getString(WALLPAPER_IMAGE_URL)

            // Save to gallery
            wallpaperImageUrl?.let {
                val bitmap = imageManager.getBitmapFromRemote(wallpaperImageUrl)

                bitmap.data?.let {
                    imageManager.saveWallpaperToGallery(wallpaperId, it)
                    success()
                } ?: failure()
            } ?: failure()
        } catch (ex: Exception) {
            Timber.tag(TAG).d(ex.toString())
            failure()
        }
    }
}