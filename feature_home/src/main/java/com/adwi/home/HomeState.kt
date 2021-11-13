package com.adwi.home

sealed class HomeEvents {
    object GetDaily : HomeEvents()
    object GetColors : HomeEvents()
    object GetCurated : HomeEvents()
}
