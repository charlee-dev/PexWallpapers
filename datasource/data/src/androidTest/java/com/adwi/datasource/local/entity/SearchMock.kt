package com.adwi.datasource.local.entity

import com.adwi.datasource.local.domain.SearchResultEntity

object SearchMock {

    val first = SearchResultEntity(
        searchQuery = "Flowers",
        wallpaperId = 1,
        queryPosition = 1
    )

    val second = SearchResultEntity(
        searchQuery = "Cars",
        wallpaperId = 2,
        queryPosition = 1
    )

    val third = SearchResultEntity(
        searchQuery = "Cars",
        wallpaperId = 3,
        queryPosition = 2
    )

    val forth = SearchResultEntity(
        searchQuery = "Flowers",
        wallpaperId = 4,
        queryPosition = 2
    )

    val list = listOf(first, second, third, forth)
}