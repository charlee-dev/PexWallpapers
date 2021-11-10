package com.adwi.core.util

import com.adwi.core.BuildConfig


object Constants {

    // PexApi
    const val BASE_URL = BuildConfig.PEX_BASE_URL
    const val API_KEY = BuildConfig.PEX_API_ACCESS_KEY
    const val AUTHORIZATION = "Authorization"
    const val CURATED_PAGE_SIZE = 20
    const val SEARCH_PAGE_SIZE = 20
    const val DEFAULT_QUERY = "Flowers"
    const val DEFAULT_CATEGORY = "Curated"

    // Pager
    const val PAGING_SIZE = 20
    const val PAGING_MAX_SIZE = 200

    // Database
    const val WALLPAPER_DATABASE = "wallpaper_database"

    // Work
    const val WORK_AUTO_WALLPAPER = "work_auto_wallpaper"
    const val WORK_AUTO_WALLPAPER_NAME = "work_auto_wallpaper_name"

    const val WORK_DOWNLOAD_WALLPAPER = "work_download_wallpaper"
    const val WORK_DOWNLOAD_WALLPAPER_NAME = "work_download_wallpaper_name"

    const val WORK_RESTORE_WALLPAPER = "work_restore_wallpaper"
    const val WORK_RESTORE_WALLPAPER_NAME = "work_restore_wallpaper_name"

    const val WORKER_AUTO_WALLPAPER_IMAGE_URL_FULL = "WallpaperUrlFull"
    const val WORKER_AUTO_WALLPAPER_NOTIFICATION_IMAGE = "WallpaperUrlTiny"

    const val NOTIFICATION_WORK = "appName_notification_work"

    const val GROUP_AUTO = "pex_auto_change"
    const val GROUP_RECOMMENDATIONS = "pex_recommendations"
    const val GROUP_INFO = "pex_info"

    const val RESTORE_AUTO_WALLPAPER = "restore_auto_wallpaper"

    const val WALLPAPER_ID = "wallpaper_id"
    const val WALLPAPER_IMAGE_URL = "wallpaper_image_url"
    const val WALLPAPER_KEY = "wallpaper_key"

    const val STORAGE_REQUEST_CODE = 233
}