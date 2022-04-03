package com.adwi.tool_automation

import android.content.Context
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.Configuration
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.adrianwitaszak.tool_image.ImageManager
import com.adwi.pexwallpapers.domain.model.Wallpaper
import com.adwi.tool_automation.automation.AutomationManager
import com.adwi.tool_automation.automation.AutomationManagerImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@RunWith(AndroidJUnit4::class)
class AutomationManagerTest {

    private lateinit var automationManager: AutomationManager
    private lateinit var context: Context
    private lateinit var imageManager: ImageManager

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext

        val config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()

        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)

        imageManager = FakeImageManager()
        val workManager = WorkManager.getInstance(context)
        automationManager = AutomationManagerImpl(imageManager, workManager)
    }

    @Test
    @Throws(Exception::class)
    fun downloadAndSaveWallpaperWork_isSuccess() {
        // Create data
        val wallpaper = Wallpaper()

        val id = automationManager.createDownloadWallpaperWork(wallpaper, false)

        val workManager = WorkManager.getInstance(context)
        WorkManagerTestInitHelper.getTestDriver(context)?.setAllConstraintsMet(id)

        // Get workInfo
        val workInfo = workManager.getWorkInfoById(id).get()

        assertThat(workInfo.state, `is`(WorkInfo.State.SUCCEEDED))
    }
}