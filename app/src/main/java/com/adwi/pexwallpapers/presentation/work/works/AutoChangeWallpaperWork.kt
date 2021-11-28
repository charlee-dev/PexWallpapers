package com.adwi.pexwallpapers.presentation.work.works

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.paging.ExperimentalPagingApi
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker.Result.failure
import androidx.work.ListenableWorker.Result.success
import androidx.work.WorkerParameters
import com.adwi.pexwallpapers.data.database.settings.SettingsDao
import com.adwi.pexwallpapers.domain.model.Wallpaper
import com.adwi.pexwallpapers.domain.repository.WallpaperRepository
import com.adwi.pexwallpapers.presentation.util.*
import com.adwi.pexwallpapers.presentation.util.Constants.WALLPAPER_ID
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
    private val wallpaperRepository: WallpaperRepository,
    private val settingsDao: SettingsDao,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {

            // Get arguments
            val wallpaperId = inputData.getInt(WALLPAPER_ID, 0)
            val wallpaper: Wallpaper = wallpaperRepository.getWallpaperById(wallpaperId).first()
            val settings = settingsDao.getSettings().first()

            val backupImage = context.getCurrentWallpaperForBackup()

            // Save backup locally
            context.backupImageToLocal(wallpaperId, backupImage)

            // Fetch bitmap using Coil
            val bitmap = context.getBitmapFromRemote(wallpaper.imageUrlPortrait)

            // Set wallpaper
            if (bitmap != null) {

                context.setAsWallpaper(
                    bitmap = bitmap,
                    setHomeScreen = settings.autoHome,
                    setLockScreen = settings.autoLock
                )

                if (settings.autoChangeWallpaper) {
                    NotificationUtil.sendNotification(
                        context = context,
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