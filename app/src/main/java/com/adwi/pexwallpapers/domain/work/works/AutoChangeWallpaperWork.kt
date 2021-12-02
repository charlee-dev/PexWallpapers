package com.adwi.pexwallpapers.domain.work.works

import android.app.NotificationManager
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.paging.ExperimentalPagingApi
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker.Result.failure
import androidx.work.ListenableWorker.Result.success
import androidx.work.WorkerParameters
import com.adwi.feature_settings.data.database.SettingsDao
import com.adwi.pexwallpapers.domain.util.Constants
import com.adwi.pexwallpapers.domain.util.Constants.WALLPAPER_ID
import com.adwi.pexwallpapers.domain.util.handleGetBitmapFromRemoteResult
import com.adwi.pexwallpapers.domain.util.sendAutoChangeWallpaperNotification
import com.adwi.pexwallpapers.domain.util.setBitmapAsWallpaper
import com.adwi.pexwallpapers.domain.work.workBackupCurrentWallpaper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import timber.log.Timber

private const val TAG = "AutoChangeWallpaperWork"

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@HiltWorker
class AutoChangeWallpaperWork @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val settingsDao: SettingsDao,
    private val notificationManager: NotificationManager
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Timber.tag(TAG).d("doWork")
        return try {

            // Get arguments
            val wallpaperId = inputData.getInt(WALLPAPER_ID, 0)
            val wallpaperImageUrl = inputData.getString(Constants.WALLPAPER_IMAGE_URL)

            //Get settings
            val settings = settingsDao.getSettings().first()

            //Check autoChangeOverWiFi setting
            if (!settings.autoChangeOverWiFi) {

                // Backup current wallpaper
                context.workBackupCurrentWallpaper(wallpaperId)

                // Fetch bitmap using Coil
                val bitmap = wallpaperImageUrl?.let { imageUrl ->
                    context.handleGetBitmapFromRemoteResult(imageUrl)
                }

                // Set wallpaper
                bitmap?.let {
                    context.setBitmapAsWallpaper(
                        bitmap = it,
                        setHomeScreen = settings.autoHome,
                        setLockScreen = settings.autoLock
                    )

                    // Check autoChangeWallpaper setting
                    if (settings.autoChangeWallpaper) {
                        context.sendAutoChangeWallpaperNotification(
                            notificationManager = notificationManager,
                            bitmap = it,
                            wallpaperId = wallpaperId
                        )
                        Timber.tag(TAG).d("Sending notification id = $wallpaperId")
                    } else {
                        Timber.tag(TAG).d("autoChangeWallpaper setting is off")
                    }
                } ?: run {
                    Timber.tag(TAG).d("bitmap is null")
                    failure()
                }
            } else {
                Timber.tag(TAG).d(
                    "AutoChangeWallpaperWork - autoChangeOverWiFi is true"
                )
            }

            Timber.tag(TAG).d("AutoChangeWallpaperWork - success")
            success()
        } catch (ex: Exception) {
            Timber.tag(TAG).d(ex.toString())
            failure()
        }
    }
}