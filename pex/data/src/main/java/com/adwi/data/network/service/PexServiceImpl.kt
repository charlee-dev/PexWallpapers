package com.adwi.data.network.service

import com.adwi.base.BuildConfig
import com.adwi.data.network.model.WallpaperResponse
import com.adwi.data.network.model.toDomainModel
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

const val BASE_URL = BuildConfig.PEX_BASE_URL
const val API_KEY = BuildConfig.PEX_API_ACCESS_KEY

const val CURATED_URL = "v1/curated"
const val SEARCH_URL = "v1/search"

const val PARAM_PAGE = "page"
const val PARAM_PER_PAGE = "per_page"

const val CURATED_PER_PAGE_VALUE = 20
const val SEARCH_PAGE_SIZE = 20
const val COLORS_PAGE_SIZE = 4
const val DAILY_PAGE_SIZE = 31

const val DEFAULT_PAGE_NUMBER = 1
const val DEFAULT_CATEGORY = "Curated"

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