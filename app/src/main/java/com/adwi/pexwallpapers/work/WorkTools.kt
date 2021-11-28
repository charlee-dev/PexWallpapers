package com.adwi.pexwallpapers.work

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.work.*
import com.adwi.pexwallpapers.domain.model.Wallpaper
import com.adwi.pexwallpapers.util.Constants.WALLPAPER_ID
import com.adwi.pexwallpapers.util.Constants.WALLPAPER_IMAGE_URL
import com.adwi.pexwallpapers.util.Constants.WORK_AUTO_WALLPAPER
import com.adwi.pexwallpapers.util.Constants.WORK_AUTO_WALLPAPER_NAME
import com.adwi.pexwallpapers.util.Constants.WORK_DOWNLOAD_WALLPAPER
import com.adwi.pexwallpapers.util.Constants.WORK_RESTORE_WALLPAPER
import com.adwi.pexwallpapers.util.Constants.WORK_RESTORE_WALLPAPER_NAME
import com.adwi.pexwallpapers.work.works.AutoChangeWallpaperWork
import com.adwi.pexwallpapers.work.works.DownloadWallpaperWork
import com.adwi.pexwallpapers.work.works.RestoreWallpaperWork
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit


private const val TAG = "WorkTools"

//private const val timeSpeeding = 30
private const val minutesWorkTimes = 3

fun Context.workCancelWorks(workTag: String) {
    WorkManager.getInstance(this)
        .cancelAllWorkByTag(workTag)
    Timber.tag(TAG).d("WorkCancelWorks - $workTag")
}

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

fun Context.workCreateDownloadWallpaperWork(wallpaper: Wallpaper, downloadOverWiFi: Boolean): UUID {
    Timber.tag(TAG).d("downloadWallpaperWork")

    val constraints = if (downloadOverWiFi) {
        Timber.tag(TAG)
            .d("Download will start when you connect to WiFi \nYou can change this in Settings")
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

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
fun Context.workSetupAutoChangeWallpaperWorks(
    favorites: List<Wallpaper>,
    timeValue: Long
) {
    Timber.tag(TAG).d("setupAutoChangeWallpaperWorks")
    workCancelWorks(WORK_AUTO_WALLPAPER)

    var multiplier = 1

    for (number in 1..minutesWorkTimes) {
        favorites.forEach { wallpaper ->

            workCreateAutoChangeWallpaperWork(
                workName = "${number}_${WORK_AUTO_WALLPAPER_NAME}_${wallpaper.id}",
                wallpaper = wallpaper,
                delay = timeValue * multiplier
            )
            multiplier++
        }
    }
}


@ExperimentalCoroutinesApi
@ExperimentalPagingApi
private fun Context.workCreateAutoChangeWallpaperWork(
    workName: String,
    wallpaper: Wallpaper,
    delay: Long
) {
    val constraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.CONNECTED)
//            .setRequiresStorageNotLow(true)
//            .setRequiresBatteryNotLow(true)
        .build()

    val work = OneTimeWorkRequestBuilder<AutoChangeWallpaperWork>()
        .setInputData(createDataForAutoChangeWallpaperWorker(wallpaper))
        .setInitialDelay(delay, TimeUnit.MINUTES)
        .setConstraints(constraints)
        .addTag(WORK_AUTO_WALLPAPER)
        .build()

    WorkManager.getInstance(this)
        .enqueueUniqueWork(
            workName,
            ExistingWorkPolicy.REPLACE,
            work
        )

    Timber.tag(TAG)
        .d("Created work: \nwallpaperId = ${wallpaper.id}, \ndelay = $delay min")
}

private fun createDataForAutoChangeWallpaperWorker(wallpaper: Wallpaper): Data {
    val builder = Data.Builder()
    builder.putInt(WALLPAPER_ID, wallpaper.id)
    Timber.tag(TAG)
        .d("createDataForAutoChangeWallpaperWorker \nimageUrl = ${wallpaper.imageUrlPortrait} \nwallpaperId = ${wallpaper.id}")
    return builder.build()
}