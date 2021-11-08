package com.adwi.base.util

import com.adwi.base.BuildConfig.DEBUG
import timber.log.Timber

object TimberFactory {
    fun setupOnDebug() {
        Timber.uprootAll()
        if (DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}