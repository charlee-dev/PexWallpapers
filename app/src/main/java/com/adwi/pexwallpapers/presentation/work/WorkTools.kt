package com.adwi.pexwallpapers.presentation.work

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.work.*
import com.adwi.pexwallpapers.domain.model.Wallpaper
import com.adwi.pexwallpapers.domain.state.Resource
import com.adwi.pexwallpapers.presentation.util.Constants.WALLPAPER_ID
import com.adwi.pexwallpapers.presentation.util.Constants.WORK_AUTO_WALLPAPER
import com.adwi.pexwallpapers.presentation.util.Constants.WORK_BACKUP_WALLPAPER
import com.adwi.pexwallpapers.presentation.util.Constants.WORK_DOWNLOAD_WALLPAPER
import com.adwi.pexwallpapers.presentation.util.Constants.WORK_RESTORE_WALLPAPER
import com.adwi.pexwallpapers.presentation.util.deleteAllBackups
import com.adwi.pexwallpapers.presentation.work.works.AutoChangeWallpaperWork
import com.adwi.pexwallpapers.presentation.work.works.BackupCurrentWallpaperWork
import com.adwi.pexwallpapers.presentation.work.works.DownloadAndSaveWallpaperWork
import com.adwi.pexwallpapers.presentation.work.works.RestoreWallpaperWork
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import java.util.concurrent.TimeUnit


@ExperimentalCoroutinesApi
@ExperimentalPagingApi
fun Context.createAutoWork(delay: Long, favorites: List<Wallpaper>): Resource {
    Timber.tag(TAG).d("createAutoWork Loading")
    return try {

        // Keeps record of any failed results
        val failedResults = mutableListOf<Resource>()

        // Schedule work for each wallpaper in favorites
        favorites.forEachIndexed { index, wallpaper ->
            Timber.tag(TAG).d("createAutoWork - setting $index")

            val repeatInterval = delay * favorites.size
            val initialDelay = (index + 1) * delay

            val workResult = workCreateAutoChangeWallpaperWork(
                workName = "${index}_${WORK_AUTO_WALLPAPER}_${wallpaper.id}",
                wallpaper = wallpaper,
                repeatInterval = repeatInterval,
                initialDelay = initialDelay
            )

            if (workResult == Resource.Error()) {
                failedResults += workResult
            }
        }

        return if (failedResults.isNotEmpty()) {
            Resource.Error("${failedResults.size} tasks failed")
        } else {
            Resource.Success()
        }
    } catch (e: Exception) {
        cancelAutoChangeWorks()
        Resource.Error(e.localizedMessage)
    }
}

// Auto change wallpaper
@ExperimentalCoroutinesApi
@ExperimentalPagingApi
private fun Context.workCreateAutoChangeWallpaperWork(
    workName: String,
    wallpaper: Wallpaper,
    repeatInterval: Long,
    initialDelay: Long
): Resource {
    try {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val work = PeriodicWorkRequestBuilder<AutoChangeWallpaperWork>(
            repeatInterval = repeatInterval,
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        )
            .setInputData(createWorkDataWithWallpaperId(wallpaper.id))
            .setInitialDelay(initialDelay, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .addTag(WORK_AUTO_WALLPAPER)
            .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                workName,
                ExistingPeriodicWorkPolicy.REPLACE,
                work
            )

        Timber.tag(TAG)
            .d("Created workCreateAutoChangeWallpaperWork: \nwallpaperId = ${wallpaper.id}, \nrepeat = $repeatInterval min \ndelay = $initialDelay")
        return Resource.Success()
    } catch (e: Exception) {
        return Resource.Error(e.localizedMessage)
    }
}

fun Context.cancelAutoChangeWorks() {

    WorkManager.getInstance(this)
        .cancelAllWorkByTag(WORK_AUTO_WALLPAPER)

    deleteAllBackups()

    Timber.tag(TAG).d("cancelAutoChangeWorks")
}

fun Context.workBackupCurrentWallpaper(wallpaperId: Int) {

    val backup = OneTimeWorkRequestBuilder<BackupCurrentWallpaperWork>()
        .setInputData(createWorkDataWithWallpaperId(wallpaperId))
        .addTag("$WORK_BACKUP_WALLPAPER${wallpaperId}")
        .build()

    WorkManager.getInstance(this)
        .enqueueUniqueWork(
            WORK_RESTORE_WALLPAPER + wallpaperId,
            ExistingWorkPolicy.KEEP,
            backup
        )

    Timber.tag(TAG).d("Created work: \nwallpaperId = $wallpaperId")
}

// Restore wallpaper from notification
fun Context.workCreateRestoreWallpaperWork(wallpaperId: Int) {

    val work = OneTimeWorkRequestBuilder<RestoreWallpaperWork>()
        .setInputData(createWorkDataWithWallpaperId(wallpaperId))
        .addTag(WORK_RESTORE_WALLPAPER)
        .build()

    WorkManager.getInstance(this)
        .enqueueUniqueWork(
            WORK_RESTORE_WALLPAPER + wallpaperId,
            ExistingWorkPolicy.KEEP,
            work
        )

    Timber.tag(TAG).d("Created work: \nwallpaperId = $wallpaperId")
}

// Download wallpaper with backup
fun Context.workCreateDownloadWallpaperWork(
    wallpaper: Wallpaper,
    downloadWallpaperOverWiFi: Boolean
) {
    val networkType = if (downloadWallpaperOverWiFi)
        NetworkType.UNMETERED else NetworkType.CONNECTED

    Timber.tag(TAG).d("Network type - $networkType")

    val constraints = Constraints.Builder()
        .setRequiredNetworkType(networkType)
        .build()

    val downloadAndSave = OneTimeWorkRequestBuilder<DownloadAndSaveWallpaperWork>()
        .setInputData(createWorkDataWithWallpaperId(wallpaper.id))
        .setConstraints(constraints)
        .addTag("$WORK_DOWNLOAD_WALLPAPER${wallpaper.id}")
        .build()

    WorkManager.getInstance(this)
        .enqueueUniqueWork(
            WORK_DOWNLOAD_WALLPAPER + wallpaper.id,
            ExistingWorkPolicy.KEEP,
            downloadAndSave
        )

    Timber.tag(TAG).d("Created workCreateDownloadWallpaperWork: \nwallpaperId = ${wallpaper.id}")
}

private fun createWorkDataWithWallpaperId(wallpaperId: Int): Data {
    val builder = Data.Builder()
    builder.putInt(WALLPAPER_ID, wallpaperId)
    Timber.tag(TAG).d("createDataForAutoChangeWallpaperWorker \nwallpaperId = $wallpaperId")
    return builder.build()
}

private const val TAG = "WorkUtil"