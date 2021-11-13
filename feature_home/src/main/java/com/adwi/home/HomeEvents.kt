package com.adwi.home

sealed class HomeEvents {
    object GetDaily : HomeEvents()
    object GetColors : HomeEvents()
    object GetCurated : HomeEvents()
    object Refresh : HomeEvents()
    data class SetCategory(val categoryName: String) : HomeEvents()
}
