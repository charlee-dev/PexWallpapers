package com.adwi.usecases

//import dagger.Module
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent

//@Module
//@InstallIn(SingletonComponent::class)
//object UseCasesModule {

//    @Singleton
//    @Provides
//    fun provideLogger() = Logger()
//
//    @Singleton
//    @Provides
//    fun provideWallpaperUseCases(
//        getCuratedWallpapers: GetCuratedWallpapers
//    ) = WallpaperUseCases(getCuratedWallpapers)
//
//    @Singleton
//    @Provides
//    fun provideGetCuratedWallpapers(
//        service: PexService,
//        logger: Logger
//    ) = GetCuratedWallpapers(service, logger)
//}