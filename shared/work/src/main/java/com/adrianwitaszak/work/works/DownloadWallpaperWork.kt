package com.adrianwitaszak.work.works

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.adrianwitaszak.image_tools.ImageTools
import com.adrianwitaszak.work.util.Constants.WALLPAPER_ID
import com.adrianwitaszak.work.util.Constants.WALLPAPER_IMAGE_URL
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

private const val TAG = "DownloadWallpaperWork"


/**
 * Download wallpaper work
 *
 * @property context
 * @property wallpaperRepository
 * @property notificationTools
 * @property imageTools
 * @property wallpaperSetter
 * @constructor
 *
 * @param params
 */
@HiltWorker
class DownloadWallpaperWork @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val imageTools: ImageTools
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            // Get arguments
            val wallpaperId = inputData.getInt(WALLPAPER_ID, 0)
            val wallpaperImageUrl = inputData.getString(WALLPAPER_IMAGE_URL)

            // Save backup locally
            wallpaperImageUrl?.let {
                imageTools.fetchRemoteAndSaveToGallery(wallpaperId, wallpaperImageUrl)
            }

            Timber.tag(TAG).d("doWork - success")
            Result.success()
        } catch (ex: Exception) {
            Timber.tag(TAG).d(ex.toString())
            Result.failure()
        }
    }
}