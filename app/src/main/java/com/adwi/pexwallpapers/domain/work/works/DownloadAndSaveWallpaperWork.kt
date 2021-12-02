package com.adwi.pexwallpapers.domain.work.works

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.adwi.pexwallpapers.domain.util.Constants.WALLPAPER_ID
import com.adwi.pexwallpapers.domain.util.Constants.WALLPAPER_IMAGE_URL
import com.adwi.pexwallpapers.domain.util.fetchRemoteAndSaveToGallery
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

private const val TAG = "DownloadWallpaperWork"

@HiltWorker
class DownloadAndSaveWallpaperWork @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {

            // Get arguments
            val wallpaperId = inputData.getInt(WALLPAPER_ID, 0)
            val wallpaperImageUrl = inputData.getString(WALLPAPER_IMAGE_URL)

            // Save to gallery
            wallpaperImageUrl?.let {
                context.fetchRemoteAndSaveToGallery(wallpaperId, wallpaperImageUrl)
            }

            Timber.tag(TAG).d("DownloadAndSaveWallpaperWork - success")

            Result.success()
        } catch (ex: Exception) {
            Timber.tag(TAG).d(ex.toString())
            Result.failure()
        }
    }
}