package com.adwi.data.network.service

import com.adwi.base.util.Constants.API_KEY
import com.adwi.base.util.Constants.BASE_URL
import com.adwi.base.util.Constants.CURATED_PER_PAGE_VALUE
import com.adwi.base.util.Constants.CURATED_URL
import com.adwi.base.util.Constants.DEFAULT_CATEGORY
import com.adwi.base.util.Constants.DEFAULT_PAGE_NUMBER
import com.adwi.base.util.Constants.PARAM_PAGE
import com.adwi.base.util.Constants.PARAM_PER_PAGE
import com.adwi.data.network.model.WallpaperResponse
import com.adwi.data.network.model.toDomainModel
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*



class PexServiceImpl(
    private val httpClient: HttpClient
) : PexService {

    override suspend fun getCuratedWallpapers() =
        httpClient.get<WallpaperResponse> {
            url(BASE_URL + CURATED_URL)
            header(HttpHeaders.Authorization, API_KEY)
            parameter(PARAM_PAGE, DEFAULT_PAGE_NUMBER)
            parameter(PARAM_PER_PAGE, CURATED_PER_PAGE_VALUE)
        }.wallpaperList.map { dto ->
            dto.toDomainModel(DEFAULT_CATEGORY)
        }
}