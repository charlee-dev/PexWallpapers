package com.adwi.core.util

import com.adwi.core.BuildConfig


object Constants {

    // PexApi
    const val BASE_URL = BuildConfig.PEX_BASE_URL
    const val API_KEY = BuildConfig.PEX_API_ACCESS_KEY
    const val AUTHORIZATION = "Authorization"

    const val DAILY_PAGE_SIZE = 31
    const val COLORS_PAGE_SIZE = 4
    const val CURATED_PAGE_SIZE = 20
    const val PAGING_PAGE_SIZE = 20

    const val DEFAULT_QUERY = "Flowers"
    const val DEFAULT_CATEGORY = "Curated"
    const val DEFAULT_DAILY_CATEGORY = "Purple Orange"

    const val REFRESH_DATA_EVERY = 5L

    // Pager
    const val PAGING_SIZE = 20
    const val PAGING_MAX_SIZE = 200

    // Database
    const val WALLPAPER_DATABASE = "wallpaper_database"

    // Notifications
    const val NOTIFICATION_WORK = "appName_notification_work"

    const val GROUP_AUTO = "pex_auto_change"
    const val GROUP_RECOMMENDATIONS = "pex_recommendations"
    const val GROUP_INFO = "pex_info"

    const val RESTORE_AUTO_WALLPAPER = "restore_auto_wallpaper"

    const val STORAGE_REQUEST_CODE = 233

    // Messages
    const val COULD_NOT_REFRESH = "Could not refresh"
}