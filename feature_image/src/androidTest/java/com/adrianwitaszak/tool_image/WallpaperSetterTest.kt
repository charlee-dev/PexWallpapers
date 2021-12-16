package com.adrianwitaszak.tool_image

import android.app.WallpaperManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import androidx.test.platform.app.InstrumentationRegistry
import com.adrianwitaszak.tool_image.util.Constants
import com.adrianwitaszak.tool_image.wallpapersetter.WallpaperSetter
import com.adrianwitaszak.tool_image.wallpapersetter.WallpaperSetterImpl
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class WallpaperSetterTest {

    private lateinit var appContext: Context
    private lateinit var wallpaperManager: WallpaperManager
    private lateinit var wallpaperSetter: WallpaperSetter

    @Before
    fun setup() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        wallpaperManager = WallpaperManager.getInstance(appContext)
        wallpaperSetter = WallpaperSetterImpl(wallpaperManager)
    }

    @After
    fun teardown() {
        val directory =
            ContextWrapper(appContext).getDir(Constants.DIR_IMAGES, Context.MODE_PRIVATE)
        directory.deleteRecursively()
    }

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.adrianwitaszak.tool_image.test", appContext.packageName)
    }

    @Test
    fun getCurrentWallpaper_checksIfBitmapNotNull_assertNotEquals() {
        val tempBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(tempBitmap)
        canvas.drawColor(Color.RED)
        // Set temp wallpaper
        wallpaperManager.setBitmap(tempBitmap)
        // Getting old wallpaper
        val bitmapOld = wallpaperSetter.getHomeScreenWallpaper()
        // Change wallpaper
        canvas.drawColor(Color.BLUE)
        wallpaperManager.setBitmap(tempBitmap)
        // Getting new wallpaper
        val bitmapNew = wallpaperSetter.getHomeScreenWallpaper()

        Assert.assertNotEquals(bitmapOld, bitmapNew)
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
        val oldWallpaper = wallpaperSetter.getHomeScreenWallpaper()

        // Change wallpaper using setWallpaper
        canvas.drawColor(Color.BLUE)
        wallpaperSetter.setWallpaper(tempBitmap, home = true, lock = false)

        // Getting new wallpaper
        val newWallpaper = wallpaperSetter.getHomeScreenWallpaper()

        Assert.assertNotEquals(oldWallpaper, newWallpaper)
    }
}