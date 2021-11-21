package com.adwi.shared.work

import androidx.paging.ExperimentalPagingApi
import androidx.work.*
import com.adwi.domain.Wallpaper
import com.adwi.shared.util.Constants.WALLPAPER_ID
import com.adwi.shared.util.Constants.WALLPAPER_IMAGE_URL
import com.adwi.shared.util.Constants.WORK_AUTO_WALLPAPER
import com.adwi.shared.util.Constants.WORK_AUTO_WALLPAPER_NAME
import com.adwi.shared.util.Constants.WORK_DOWNLOAD_WALLPAPER
import com.adwi.shared.util.Constants.WORK_RESTORE_WALLPAPER
import com.adwi.shared.util.Constants.WORK_RESTORE_WALLPAPER_NAME
import com.adwi.shared.work.works.AutoChangeWallpaperWork
import com.adwi.shared.work.works.DownloadWallpaperWork
import com.adwi.shared.work.works.RestoreWallpaperWork
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


private const val TAG = "WorkTools"
private const val timeSpeeding = 30
private const val minutesWorkTimes = 3

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class WorkTools @Inject constructor(
    private val workManager: WorkManager
) {

    fun cancelWorks(workTag: String) {
        workManager.cancelAllWorkByTag(workTag)
        Timber.tag(TAG).d("cancelWorks - $workTag")
    }

    fun createRestoreWallpaperWork(wallpaperId: String) {
        val builder = Data.Builder()
            .putString(WALLPAPER_ID, wallpaperId)
            .build()

        val work = OneTimeWorkRequestBuilder<RestoreWallpaperWork>()
            .setInputData(builder)
            .addTag(WORK_RESTORE_WALLPAPER)
            .build()

        workManager.enqueueUniqueWork(
            WORK_RESTORE_WALLPAPER_NAME + wallpaperId,
            ExistingWorkPolicy.KEEP,
            work
        )

        Timber.tag(TAG).d("Created work: \nwallpaperId = $wallpaperId")
    }

    fun createDownloadWallpaperWork(wallpaper: Wallpaper, wifiOnly: Boolean): UUID {
        Timber.tag(TAG).d("downloadWallpaperWork")

        val constraints = if (wifiOnly) {
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build()
        } else {
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

        workManager.enqueueUniqueWork(
            WORK_AUTO_WALLPAPER_NAME + wallpaper.id,
            ExistingWorkPolicy.KEEP,
            work
        )

        if (wifiOnly) {
            Timber.tag(TAG)
                .d("Download will start when you connect to WiFi \nYou can change this in Settings")
        }

        Timber.tag(TAG)
            .d("Created work: \nwallpaperId = ${wallpaper.id}")

        return work.id
    }

    fun setupAutoChangeWallpaperWorks(
        favorites: List<Wallpaper>,
        timeValue: Long
    ) {
        Timber.tag(TAG).d("setupAutoChangeWallpaperWorks")
        cancelWorks(WORK_AUTO_WALLPAPER)

        var multiplier = 1

        for (number in 1..minutesWorkTimes) {
            favorites.forEach { wallpaper ->

                createAutoChangeWallpaperWork(
                    workName = "${number}_${WORK_AUTO_WALLPAPER_NAME}_${wallpaper.id}",
                    wallpaper = wallpaper,
                    delay = timeValue * multiplier
                )
                multiplier++
            }
        }
    }


    private fun createAutoChangeWallpaperWork(
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

        workManager.enqueueUniqueWork(
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
}