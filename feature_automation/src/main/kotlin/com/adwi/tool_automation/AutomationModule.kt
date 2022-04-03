package com.adwi.tool_automation

import android.app.NotificationManager
import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.work.WorkManager
import com.adrianwitaszak.tool_image.imagemanager.ImageManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AutomationModule {

    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    @Provides
    @Singleton
    fun provideAutomationManager(
        imageManager: ImageManager,
        workManager: WorkManager
    ): AutomationManager = AutomationManagerImpl(imageManager, workManager)

    @Provides
    @Singleton
    fun provideWorkManager(
        @ApplicationContext context: Context
    ): WorkManager = WorkManager.getInstance(context)

    @Provides
    @Singleton
    fun provideNotificationsManager(
        @ApplicationContext context: Context
    ): NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
}