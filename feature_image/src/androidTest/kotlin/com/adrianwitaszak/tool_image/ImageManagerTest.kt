package com.adrianwitaszak.tool_image

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.adrianwitaszak.tool_image.imagemanager.ImageManager
import com.adrianwitaszak.tool_image.imagemanager.ImageManagerImpl
import com.adrianwitaszak.tool_image.util.Constants
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File


@RunWith(AndroidJUnit4::class)
class ImageManagerTest {

    private lateinit var imageManager: ImageManager
    private lateinit var appContext: Context

    @Before
    fun setup() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        imageManager = ImageManagerImpl(context = appContext)
    }

    @After
    fun teardown() {
        val directory =
            ContextWrapper(appContext).getDir(Constants.DIR_IMAGES, Context.MODE_PRIVATE)
        directory.deleteRecursively()
    }

    @Test
    fun saveWallpaperLocally_assertFileExist() {
        // Prep bitmap
        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)

        // Deleting directory
        val directory =
            ContextWrapper(appContext).getDir(Constants.DIR_IMAGES, Context.MODE_PRIVATE)
        directory.deleteRecursively()

        // Save bitmap
        val wallpaperId = 1234
        imageManager.saveWallpaperLocally(wallpaperId, bitmap)

        assertTrue(directory.isDirectory)

        val fileName = "${Constants.BACKUP_WALLPAPER}$wallpaperId${Constants.IMAGE_FILE_EXT}"
        val file = File(directory, fileName)

        assertTrue(file.isFile)
    }

    @Test
    fun deleteAllBackups_checkIfExist_assertFalse() {
        val directory =
            ContextWrapper(appContext).getDir(Constants.DIR_IMAGES, Context.MODE_PRIVATE)
        imageManager.deleteAllBackups()
        assertFalse(directory.isDirectory)
    }

    @Test
    fun deleteBackupBitmap_createFile_deleteIt_checkIfExist_assertFalse() {
        // Prep bitmap
        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val wallpaperId = 1234
        val directory =
            ContextWrapper(appContext).getDir(Constants.DIR_IMAGES, Context.MODE_PRIVATE)
        val fileName = "${Constants.BACKUP_WALLPAPER}$wallpaperId${Constants.IMAGE_FILE_EXT}"
        val file = File(directory, fileName)
        // Save bitmap
        imageManager.saveWallpaperLocally(wallpaperId, bitmap)

        assertTrue(file.exists())

        imageManager.deleteBackup(wallpaperId.toString())
        assertFalse(file.exists())
    }
}