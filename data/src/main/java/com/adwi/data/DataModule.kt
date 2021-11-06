package com.adwi.data

import android.util.Log
import com.adwi.data.network.service.PexService
import com.adwi.data.network.service.PexServiceImpl
import com.adwi.data.repository.Repository
import com.adwi.data.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(
                    kotlinx.serialization.json.Json {
                        ignoreUnknownKeys = true // if the server sends extra fields, ignore them
                    }
                )
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            install(ResponseObserver) {
                onResponse { response ->
                    Log.d("HTTP status:", "${response.status.value}")
                }
            }
        }
    }

    @Singleton
    @Provides
    fun providePexService(
        httpClient: HttpClient
    ) = PexServiceImpl(httpClient)

    @Singleton
    @Provides
    fun provideWallpaperRepository(service: PexService): Repository =
        RepositoryImpl(service)
}