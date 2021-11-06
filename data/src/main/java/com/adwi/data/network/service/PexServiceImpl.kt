package com.adwi.data.network.service

import com.adwi.base.util.Constants
import com.adwi.data.network.model.WallpaperResponse
import com.adwi.data.network.model.toDomainModel
import io.ktor.client.*
import io.ktor.client.request.*

class PexServiceImpl(
    private val httpClient: HttpClient,
) : PexService {

    override suspend fun getCuratedWallpapers() =
        httpClient.get<WallpaperResponse> {
            url(Constants.BASE_URL)
        }.wallpaperList.map { it.toDomainModel() }
}