package com.adrianwitaszak.tool_image

import android.app.WallpaperManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.adrianwitaszak.tool_image.imagemanager.ImageManagerImpl
import com.adrianwitaszak.tool_image.util.Constants
import com.adrianwitaszak.tool_image.util.compressStream
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File


@RunWith(AndroidJUnit4::class)
class ImageManagerTest {

    private lateinit var imageManager: ImageManager
    private lateinit var wallpaperManager: WallpaperManager
    private lateinit var appContext: Context

    @Before
    fun setup() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        wallpaperManager = WallpaperManager.getInstance(appContext)
        imageManager = ImageManagerImpl(context = appContext, wallpaperManager)
    }

    @After
    fun teardown() {
        val directory =
            ContextWrapper(appContext).getDir(Constants.DIR_IMAGES, Context.MODE_PRIVATE)
        directory.deleteRecursively()
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.adrianwitaszak.tool_image.test", appContext.packageName)
    }

    @Test
    fun getCurrentWallpaper_checksIfBitmapNotNull_assertNotEquals() {
        val tempBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(tempBitmap)
        canvas.drawColor(Color.RED)
        // Set temp wallpaper
        wallpaperManager.setBitmap(tempBitmap)
        // Getting old wallpaper
        val bitmapOld = imageManager.getCurrentWallpaper()
        // Change wallpaper
        canvas.drawColor(Color.BLUE)
        wallpaperManager.setBitmap(tempBitmap)
        // Getting new wallpaper
        val bitmapNew = imageManager.getCurrentWallpaper()

        assertNotEquals(bitmapOld.data, bitmapNew.data)
    }

    @Test
    fun setWallpaper_compareOld_New_assertNotEquals() {
        // Prep bitmap
        val tempBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(tempBitmap)
        canvas.drawColor(Color.RED)

        // Set temp wallpaper
        wallpaperManager.setBitmap(tempBitmap)

        // Getting old wallpaper
        val oldWallpaper = imageManager.getCurrentWallpaper()

        // Change wallpaper using setWallpaper
        canvas.drawColor(Color.BLUE)
        imageManager.setWallpaper(tempBitmap, home = true, lock = false)

        // Getting new wallpaper
        val newWallpaper = imageManager.getCurrentWallpaper()

        assertNotEquals(oldWallpaper.data, newWallpaper.data)
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
        file.compressStream(bitmap)

        assertTrue(file.exists())

        imageManager.deleteBackupBitmap(wallpaperId.toString())
        assertFalse(file.exists())
    }
}