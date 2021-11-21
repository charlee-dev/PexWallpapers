package com.adrianwitaszak.work.works

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker.Result.failure
import androidx.work.ListenableWorker.Result.success
import androidx.work.WorkerParameters
import com.adrianwitaszak.image_tools.ImageTools
import com.adrianwitaszak.wallpaper_setter.WallpaperSetter
import com.adrianwitaszak.work.util.Constants.WALLPAPER_ID
import com.adwi.domain.Wallpaper
import com.adwi.pexwallpapers.shared.tools.notification.Channel
import com.adwi.pexwallpapers.shared.tools.notification.NotificationTools
import com.adwi.repository.settings.SettingsRepository
import com.adwi.repository.wallpaper.WallpaperRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import timber.log.Timber

private const val TAG = "AutoChangeWallpaperWork"

@HiltWorker
class AutoChangeWallpaperWork @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val wallpaperRepository: WallpaperRepository,
    private val settingsRepository: SettingsRepository,
    private val notificationTools: NotificationTools,
    private val imageTools: ImageTools,
    private val wallpaperSetter: WallpaperSetter
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {

            // Get arguments
            val wallpaperId = inputData.getInt(WALLPAPER_ID, 0)
            val wallpaper: Wallpaper = wallpaperRepository.getWallpaperById(wallpaperId).first()
            val settings = settingsRepository.getSettings().first()

            val backupImage = wallpaperSetter.getCurrentWallpaperForBackup()

            // Save backup locally
            imageTools.backupImageToLocal(wallpaperId, backupImage)

            // Fetch bitmap using Coil
            val bitmap = imageTools.getBitmapFromRemote(wallpaper.imageUrlPortrait)

            // Set wallpaper
            if (bitmap != null) {

                wallpaperSetter.setWallpaper(
                    bitmap = bitmap,
                    setHomeScreen = settings.autoHome,
                    setLockScreen = settings.autoLock
                )

                if (settings.autoChangeWallpaper) {
                    notificationTools.sendNotification(
                        channelId = Channel.AUTO_WALLPAPER,
                        bitmap = bitmap,
                        id = wallpaperId,
                        wallpaperId = wallpaperId
                    )
                } else {
                    Timber.tag(TAG).d("some settings are off")
                }
            } else {
                Timber.tag(TAG).d("odWork - bitmap null")
            }
            Timber.tag(TAG).d("doWork - success")
            success()
        } catch (ex: Exception) {
            Timber.tag(TAG).d(ex.toString())
            failure()
        }
    }
}