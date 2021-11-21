package com.adrianwitaszak.work.works

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.adrianwitaszak.image_tools.ImageTools
import com.adrianwitaszak.wallpaper_setter.WallpaperSetter
import com.adrianwitaszak.work.util.Constants.WALLPAPER_ID
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

                wallpaperSetter.setWallpaper(
                    bitmap = bitmap,
                    setHomeScreen = true,
                    setLockScreen = false
                )

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