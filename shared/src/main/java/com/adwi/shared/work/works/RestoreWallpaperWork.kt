package com.adwi.shared.work.works

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.adwi.shared.image.ImageTools
import com.adwi.shared.setter.WallpaperSetter
import com.adwi.shared.util.Constants.WALLPAPER_ID
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

private const val TAG = "DownloadWallpaperWork"


@HiltWorker
class RestoreWallpaperWork @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val imageTools: ImageTools,
    private val wallpaperSetter: WallpaperSetter
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            // Get arguments
            val wallpaperId = inputData.getString(WALLPAPER_ID)

            wallpaperId?.let {

                val bitmap = imageTools.restoreBackup(wallpaperId)

                bitmap?.let {
                    wallpaperSetter.setWallpaper(
                        bitmap = bitmap,
                        setHomeScreen = true,
                        setLockScreen = false
                    )
                }

                imageTools.deleteBackupBitmap(wallpaperId)
            }

            Timber.tag(TAG).d("doWork - success")
            Result.success()
        } catch (ex: Exception) {
            Timber.tag(TAG).d(ex.toString())
            Result.failure()
        }
    }
}