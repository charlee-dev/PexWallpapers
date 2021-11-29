package com.adwi.pexwallpapers.presentation.work

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.work.*
import com.adwi.pexwallpapers.domain.model.Wallpaper
import com.adwi.pexwallpapers.domain.state.Resource
import com.adwi.pexwallpapers.presentation.util.Constants.WALLPAPER_ID
import com.adwi.pexwallpapers.presentation.util.Constants.WALLPAPER_IMAGE_URL
import com.adwi.pexwallpapers.presentation.util.Constants.WORK_AUTO_WALLPAPER
import com.adwi.pexwallpapers.presentation.util.Constants.WORK_AUTO_WALLPAPER_NAME
import com.adwi.pexwallpapers.presentation.util.Constants.WORK_DOWNLOAD_WALLPAPER
import com.adwi.pexwallpapers.presentation.util.Constants.WORK_RESTORE_WALLPAPER
import com.adwi.pexwallpapers.presentation.util.Constants.WORK_RESTORE_WALLPAPER_NAME
import com.adwi.pexwallpapers.presentation.util.deleteAllBackups
import com.adwi.pexwallpapers.presentation.work.works.AutoChangeWallpaperWork
import com.adwi.pexwallpapers.presentation.work.works.DownloadWallpaperWork
import com.adwi.pexwallpapers.presentation.work.works.RestoreWallpaperWork
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import java.util.*
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
            val initialDelay = index * delay

            val workResult = workCreateAutoChangeWallpaperWork(
                workName = "${index}_${WORK_AUTO_WALLPAPER_NAME}_${wallpaper.id}",
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


private const val numberOfWorkCycles = 3

fun Context.cancelAutoChangeWorks() {

    WorkManager.getInstance(this)
        .cancelAllWorkByTag(WORK_AUTO_WALLPAPER)

    deleteAllBackups()

    Timber.tag(TAG).d("cancelAutoChangeWorks")
}

// Restore wallpaper from notification
fun Context.workCreateRestoreWallpaperWork(wallpaperId: String) {
    val builder = Data.Builder()
        .putString(WALLPAPER_ID, wallpaperId)
        .build()

    val work = OneTimeWorkRequestBuilder<RestoreWallpaperWork>()
        .setInputData(builder)
        .addTag(WORK_RESTORE_WALLPAPER)
        .build()

    WorkManager.getInstance(this)
        .enqueueUniqueWork(
            WORK_RESTORE_WALLPAPER_NAME + wallpaperId,
            ExistingWorkPolicy.KEEP,
            work
        )

    Timber.tag(TAG).d("Created work: \nwallpaperId = $wallpaperId")
}

// Download wallpaper
fun Context.workCreateDownloadWallpaperWork(
    wallpaper: Wallpaper,
    downloadWallpaperOverWiFi: Boolean
): UUID {
    Timber.tag(TAG).d("workCreateDownloadWallpaperWork")

    val constraints = if (downloadWallpaperOverWiFi) {
        Timber.tag(TAG)
            .d("Download will start when you connect to WiFi")
        Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

    } else {
        Timber.tag(TAG)
            .d("Starting download")
        Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    }

    val builder = Data.Builder()
        .putInt(WALLPAPER_ID, wallpaper.id)
        .putString(WALLPAPER_IMAGE_URL, wallpaper.imageUrlPortrait)
        .build()

    val workTag = "$WORK_DOWNLOAD_WALLPAPER${wallpaper.id}"

    val work = OneTimeWorkRequestBuilder<DownloadWallpaperWork>()
        .setInputData(builder)
        .setConstraints(constraints)
        .addTag(workTag) // TODO()
        .build()

    WorkManager.getInstance(this)
        .enqueueUniqueWork(
            WORK_AUTO_WALLPAPER_NAME + wallpaper.id,
            ExistingWorkPolicy.KEEP,
            work
        )

    Timber.tag(TAG)
        .d("Created work: \nwallpaperId = ${wallpaper.id}")

    return work.id
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
            .setInputData(createDataForAutoChangeWallpaperWorker(wallpaper))
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
            .d("Created work: \nwallpaperId = ${wallpaper.id}, \ndelay = $repeatInterval min")
        return Resource.Success()
    } catch (e: Exception) {
        return Resource.Error(message = e.localizedMessage)
    }
}

private fun createDataForAutoChangeWallpaperWorker(wallpaper: Wallpaper): Data {
    val builder = Data.Builder()
    builder.putInt(WALLPAPER_ID, wallpaper.id)
    Timber.tag(TAG)
        .d("createDataForAutoChangeWallpaperWorker \nimageUrl = ${wallpaper.imageUrlPortrait} \nwallpaperId = ${wallpaper.id}")
    return builder.build()
}

private const val TAG = "WorkUtil"