package com.adrianwitaszak.tool_image

import android.app.WallpaperManager
import android.content.Context
import com.adrianwitaszak.tool_image.imagemanager.ImageManager
import com.adrianwitaszak.tool_image.imagemanager.ImageManagerImpl
import com.adrianwitaszak.tool_image.wallpapersetter.WallpaperSetter
import com.adrianwitaszak.tool_image.wallpapersetter.WallpaperSetterImpl
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
        @ApplicationContext context: Context
    ): ImageManager = ImageManagerImpl(context)

    @Provides
    @Singleton
    fun provideWallpaperSetter(
        wallpaperManager: WallpaperManager
    ): WallpaperSetter = WallpaperSetterImpl(wallpaperManager)

    @Provides
    @Singleton
    fun provideWallpaperManager(
        @ApplicationContext context: Context
    ): WallpaperManager = WallpaperManager.getInstance(context)
}