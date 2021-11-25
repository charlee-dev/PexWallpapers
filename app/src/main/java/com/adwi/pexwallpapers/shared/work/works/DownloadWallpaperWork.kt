package com.adwi.pexwallpapers.shared.work.works

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.adwi.pexwallpapers.shared.image.ImageTools
import com.adwi.pexwallpapers.util.Constants.WALLPAPER_ID
import com.adwi.pexwallpapers.util.Constants.WALLPAPER_IMAGE_URL
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import timber.log.Timber

private const val TAG = "DownloadWallpaperWork"

@HiltWorker
class DownloadWallpaperWork @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val imageTools: ImageTools
) : CoroutineWorker(context, params) {

    companion object {
        const val Progress = "Progress"
        private const val delayDuration = 1L
    }

    override suspend fun doWork(): Result {
        return try {
            val firstUpdate = workDataOf(Progress to 0)
            val lastUpdate = workDataOf(Progress to 100)
            setProgress(firstUpdate)
            // Get arguments
            val wallpaperId = inputData.getInt(WALLPAPER_ID, 0)
            val wallpaperImageUrl = inputData.getString(WALLPAPER_IMAGE_URL)

            // Save backup locally
            wallpaperImageUrl?.let {
                imageTools.fetchRemoteAndSaveToGallery(wallpaperId, wallpaperImageUrl)
            }

            Timber.tag(TAG).d("doWork - success")
            delay(delayDuration)
            setProgress(lastUpdate)
            Result.success()
        } catch (ex: Exception) {
            Timber.tag(TAG).d(ex.toString())
            Result.failure()
        }
    }
}