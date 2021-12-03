package com.adwi.tool_automation.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.adrianwitaszak.tool_image.ImageManager
import com.adwi.tool_automation.util.Constants.ACTION_AUTO
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class OnDismissReceiver : BroadcastReceiver() {

    @Inject
    lateinit var imageManager: ImageManager

    override fun onReceive(context: Context?, intent: Intent?) {
        val tag: String = javaClass.simpleName
        val wallpaperId = intent?.getStringExtra(ACTION_AUTO)
        wallpaperId?.let {
            // Delete backup wallpaper if notification dismissed
            imageManager.deleteBackupBitmap(wallpaperId)
            Timber.tag(tag)
        }
    }
}