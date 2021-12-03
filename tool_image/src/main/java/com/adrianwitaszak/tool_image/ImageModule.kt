package com.adrianwitaszak.tool_image

import android.app.WallpaperManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageModule {

    @Provides
    @Singleton
    fun provideImageManager(
        @ApplicationContext context: Context,
        wallpaperManager: WallpaperManager
    ): ImageManager = ImageManagerImpl(context, wallpaperManager)

    @Provides
    @Singleton
    fun provideWallpaperManager(
        @ApplicationContext context: Context
    ): WallpaperManager = WallpaperManager.getInstance(context)
}