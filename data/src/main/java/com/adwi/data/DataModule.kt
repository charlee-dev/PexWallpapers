package com.adwi.data

//import dagger.Module
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent

//@Module
//@InstallIn(SingletonComponent::class)
//object DataModule {

//    @Singleton
//    @Provides
//    fun provideHttpClient(): HttpClient {
//        return HttpClient(Android) {
//            install(JsonFeature) {
//                serializer = KotlinxSerializer(
//                    kotlinx.serialization.json.Json {
//                        ignoreUnknownKeys = true // if the server sends extra fields, ignore them
//                    }
//                )
//            }
//            install(Logging) {
//                logger = Logger.DEFAULT
//                level = LogLevel.ALL
//            }
//            install(ResponseObserver) {
//                onResponse { response ->
//                    Log.d("HTTP status:", "${response.status.value}")
//                }
//            }
//        }
//    }
//
//    @Singleton
//    @Provides
//    fun providePexService(
//        httpClient: HttpClient,
//        apikey: String
//    ) = PexServiceImpl(httpClient, apikey)
//
//    @Singleton
//    @Provides
//    fun provideApiKey() = Constants.API_KEY
//}