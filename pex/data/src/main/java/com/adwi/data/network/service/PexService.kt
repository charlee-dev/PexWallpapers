package com.adwi.data.network.service

import com.adwi.domain.Wallpaper
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*

interface PexService {

    suspend fun getCuratedWallpapers(): List<Wallpaper>

    companion object Factory {
        fun build(): PexService {
            return PexServiceImpl(
                httpClient = HttpClient(Android) {
                    install(JsonFeature) {
                        serializer = KotlinxSerializer(
                            kotlinx.serialization.json.Json {
                                ignoreUnknownKeys =
                                    true // if the server sends extra fields, ignore them
                            }
                        )
                    }
//                    install(Logging) {
//                        logger = Logger.DEFAULT
//                        level = LogLevel.ALL
//                    }
//                    install(ResponseObserver) {
//                        onResponse { response ->
//                            Log.d("HTTP status:", "${response.status.value}")
//                        }
//                    }
                }
            )
        }
    }
}