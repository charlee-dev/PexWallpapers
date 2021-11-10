//package com.adwi.pexwallpapers.di
//
//import android.app.Application
//import WallpaperInteractors
//import GetCuratedWallpapers
//import com.squareup.sqldelight.android.AndroidSqliteDriver
//import com.squareup.sqldelight.db.SqlDriver
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Named
//import javax.inject.Singleton
//
//private const val WALLPAPER_ANDROID_DRIVER = "wallpaper_android_driver"
//
//@Module
//@InstallIn(SingletonComponent::class)
//object UseCasesModule {
//
//    @Provides
//    @Singleton
//    @Named(WALLPAPER_ANDROID_DRIVER)
//    fun provideAndroidDriver(app: Application): SqlDriver = AndroidSqliteDriver(
//        schema = WallpaperInteractors.schema,
//        context = app,
//        name = WallpaperInteractors.dbName,
//    )
//
//    @Provides
//    @Singleton
//    fun provideWallpaperUseCases(
//        @Named(WALLPAPER_ANDROID_DRIVER) sqlDriver: SqlDriver
//    ): WallpaperInteractors = WallpaperInteractors.build(sqlDriver)
//
//    @Provides
//    @Singleton
//    fun provideGetCuratedWallpapers(
//        wallpaperInteractors: WallpaperInteractors
//    ): GetCuratedWallpapers = wallpaperInteractors.getCuratedWallpapers
//}