package com.adwi.pexwallpapers.work.works

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.adwi.pexwallpapers.util.Constants.WALLPAPER_ID
import com.adwi.pexwallpapers.util.deleteBackupBitmap
import com.adwi.pexwallpapers.util.restoreBackup
import com.adwi.pexwallpapers.util.setAsWallpaper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

private const val TAG = "DownloadWallpaperWork"


@HiltWorker
class RestoreWallpaperWork @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            // Get arguments
            val wallpaperId = inputData.getString(WALLPAPER_ID)

            wallpaperId?.let {
                val bitmap = context.restoreBackup(wallpaperId)

                bitmap?.let {
                    context.setAsWallpaper(
                        bitmap = bitmap,
                        setHomeScreen = true,
                        setLockScreen = false
                    )
                }

                context.deleteBackupBitmap(wallpaperId)
            }

            Timber.tag(TAG).d("doWork - success")
            Result.success()
        } catch (ex: Exception) {
            Timber.tag(TAG).d(ex.toString())
            Result.failure()
        }
    }
}