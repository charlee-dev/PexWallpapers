package com.adwi.pexwallpapers

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.paging.ExperimentalPagingApi
import androidx.work.Configuration
import com.adwi.shared.notifications.NotificationTools
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import javax.inject.Inject


@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltAndroidApp
class PexApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var notificationTools: NotificationTools

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        notificationTools.setupNotifications()
    }
}