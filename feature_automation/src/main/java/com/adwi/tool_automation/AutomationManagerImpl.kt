package com.adwi.tool_automation

import androidx.paging.ExperimentalPagingApi
import androidx.work.*
import com.adrianwitaszak.tool_image.imagemanager.ImageManager
import com.adwi.pexwallpapers.domain.model.Wallpaper
import com.adwi.tool_automation.util.Constants.WALLPAPER_ID
import com.adwi.tool_automation.util.Constants.WALLPAPER_IMAGE_URL
import com.adwi.tool_automation.util.Constants.WORK_AUTO_WALLPAPER
import com.adwi.tool_automation.util.Constants.WORK_BACKUP_WALLPAPER
import com.adwi.tool_automation.util.Constants.WORK_DOWNLOAD_WALLPAPER
import com.adwi.tool_automation.util.Constants.WORK_RESTORE_WALLPAPER
import com.adwi.tool_automation.workers.AutoChangeWallpaperWork
import com.adwi.tool_automation.workers.BackupCurrentWallpaperWork
import com.adwi.tool_automation.workers.DownloadAndSaveWallpaperWork
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class AutomationManagerImpl @Inject constructor(
    private val imageManager: ImageManager,
    private val workManager: WorkManager
) : AutomationManager {

    private val tag = javaClass.name

    override fun startAutoChangeWallpaperWork(delay: Long, favorites: List<Wallpaper>) {
        cancelAutoChangeWorks()
        favorites.forEachIndexed { index, wallpaper ->
            Timber.tag(tag).d("createAutoWork - setting $index")

            val repeatInterval = delay * favorites.size
            val initialDelay = (index + 1) * delay

            workCreateAutoChangeWallpaperWork(
                workName = "${index}_${WORK_AUTO_WALLPAPER}_${wallpaper.id}",
                wallpaper = wallpaper,
                repeatInterval = repeatInterval,
                initialDelay = initialDelay
            )
        }
    }

    override fun workCreateAutoChangeWallpaperWork(
        workName: String,
        wallpaper: Wallpaper,
        repeatInterval: Long,
        initialDelay: Long
    ) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val work = PeriodicWorkRequestBuilder<AutoChangeWallpaperWork>(
            repeatInterval = repeatInterval,
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        )
            .setInputData(createWorkData(wallpaper.id))
            .setInitialDelay(initialDelay, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .addTag(WORK_AUTO_WALLPAPER)
            .build()

        workManager.enqueueUniquePeriodicWork(
            workName,
            ExistingPeriodicWorkPolicy.REPLACE,
            work
        )
    }

    override fun cancelAutoChangeWorks() {
        workManager.cancelAllWorkByTag(WORK_AUTO_WALLPAPER)
        imageManager.deleteAllBackups()
        Timber.tag(tag).d("cancelAutoChangeWorks")
    }

    override fun backupCurrentWallpaper(wallpaperId: Int) {
        val backup = OneTimeWorkRequestBuilder<BackupCurrentWallpaperWork>()
            .setInputData(createWorkData(wallpaperId))
            .addTag("$WORK_BACKUP_WALLPAPER${wallpaperId}")
            .build()

        workManager.enqueueUniqueWork(
            WORK_RESTORE_WALLPAPER + wallpaperId,
            ExistingWorkPolicy.KEEP,
            backup
        )

        Timber.tag(tag).d("Created work: \nwallpaperId = $wallpaperId")
    }

    override fun createDownloadWallpaperWork(
        wallpaper: Wallpaper,
        downloadWallpaperOverWiFi: Boolean
    ) {
        val networkType = if (downloadWallpaperOverWiFi)
            NetworkType.UNMETERED else NetworkType.CONNECTED

        Timber.tag(tag).d("Network type - $networkType")

        val data = Data.Builder()
            .putInt(WALLPAPER_ID, wallpaper.id)
            .putString(WALLPAPER_IMAGE_URL, wallpaper.imageUrlPortrait)
            .build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(networkType)
            .build()

        val downloadAndSave = OneTimeWorkRequestBuilder<DownloadAndSaveWallpaperWork>()
            .setInputData(data)
            .setConstraints(constraints)
            .addTag("$WORK_DOWNLOAD_WALLPAPER${wallpaper.id}")
            .build()

        workManager.enqueueUniqueWork(
            WORK_DOWNLOAD_WALLPAPER + wallpaper.id,
            ExistingWorkPolicy.KEEP,
            downloadAndSave
        )

        Timber.tag(tag)
            .d("createDownloadWallpaperWork: \nwallpaperId - ${wallpaper.id} \nuuid - ${downloadAndSave.id}")
    }

    override fun createWorkData(wallpaperId: Int): Data =
        Data.Builder()
            .putInt(WALLPAPER_ID, wallpaperId)
            .build()
}