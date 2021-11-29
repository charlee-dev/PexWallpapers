package com.adwi.pexwallpapers.presentation.work.works

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker.Result.failure
import androidx.work.ListenableWorker.Result.success
import androidx.work.WorkerParameters
import com.adwi.pexwallpapers.domain.state.DataState
import com.adwi.pexwallpapers.presentation.util.Constants.WALLPAPER_ID
import com.adwi.pexwallpapers.presentation.util.backupImageToLocal
import com.adwi.pexwallpapers.presentation.util.getCurrentWallpaperForBackup
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

private const val TAG = "AutoChangeWallpaperWork"

@HiltWorker
class BackupCurrentWallpaperWork @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Timber.tag(TAG).d("doWork")
        return try {

            // Get wallpaperId
            val wallpaperId = inputData.getInt(WALLPAPER_ID, 0)

            // Backup current wallpaper
            when (val backupState = context.getCurrentWallpaperForBackup()) {
                is DataState.Error -> {
                    Timber.tag(TAG).d("BackupCurrentWallpaperWork FAILURE get current wallpaper")
                    Timber.tag(TAG).d(
                        backupState.error?.localizedMessage ?: "Can't retrieve current wallpaper"
                    )
                    failure()
                }
                is DataState.Success -> {
                    backupState.data?.let { bitmap ->
                        // Save backup locally
                        try {
                            context.backupImageToLocal(wallpaperId, bitmap)
                            Timber.tag(TAG).d("BackupCurrentWallpaperWork SUCCESS get and save")
                            success()
                        } catch (e: Exception) {
                            Timber.tag(TAG)
                                .d("BackupCurrentWallpaperWork SUCCESS get and FAILURE save")
                            Timber.tag(TAG).d(e.localizedMessage)
                            failure()
                        }
                    }
                }
                else -> {
                    // Loading
                }
            }
            success()
        } catch (e: Exception) {
            Timber.tag(TAG).d(e.toString())
            failure()
        }
    }
}