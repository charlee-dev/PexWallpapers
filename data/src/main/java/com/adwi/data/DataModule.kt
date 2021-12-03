package com.adwi.data

import android.content.Context
import androidx.room.Room
import com.adwi.data.Constants.WALLPAPER_DATABASE
import com.adwi.data.database.WallpaperDatabase
import com.adwi.data.database.dao.CategoryDao
import com.adwi.data.database.dao.DailyDao
import com.adwi.data.database.dao.SearchDao
import com.adwi.data.database.dao.WallpapersDao
import com.adwi.data.network.PexService
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideWallpaperDatabase(@ApplicationContext context: Context): WallpaperDatabase =
        Room
            .databaseBuilder(
                context,
                WallpaperDatabase::class.java,
                WALLPAPER_DATABASE
            )
            .build()

    @Provides
    @Singleton
    fun provideWallpaperDao(wallpaperDatabase: WallpaperDatabase): WallpapersDao =
        wallpaperDatabase.wallpaperDao()

    @Provides
    @Singleton
    fun provideDailyDao(wallpaperDatabase: WallpaperDatabase): DailyDao =
        wallpaperDatabase.dailyDao()

    @Provides
    @Singleton
    fun provideCategoryDao(wallpaperDatabase: WallpaperDatabase): CategoryDao =
        wallpaperDatabase.categoryDao()

    @Provides
    @Singleton
    fun provideSearchDao(wallpaperDatabase: WallpaperDatabase): SearchDao =
        wallpaperDatabase.searchDao()

//    @Singleton
//    @Provides
//    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
//        val logging = HttpLoggingInterceptor { message ->
//            Timber.d(message)
//        }
//        logging.level = HttpLoggingInterceptor.Level.BASIC
//        return logging
//    }

    @Singleton
    @Provides
    fun provideNetworkInterceptor(): Interceptor =
        Interceptor {
            val request = it.request().newBuilder()
                .addHeader(Constants.AUTHORIZATION, Constants.API_KEY)
                .build()
            it.proceed(request)
        }

    @Singleton
    @Provides
    fun provideHttpClient(
//        loggingInterceptor: HttpLoggingInterceptor,
        networkInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
//        .addInterceptor(loggingInterceptor)
        .addNetworkInterceptor(networkInterceptor)
        .readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory())
            .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): PexService =
        retrofit.create(PexService::class.java)
}