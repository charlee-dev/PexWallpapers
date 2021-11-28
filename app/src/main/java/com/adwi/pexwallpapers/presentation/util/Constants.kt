package com.adwi.pexwallpapers.presentation.util

import com.adwi.pexwallpapers.BuildConfig


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
    const val GROUP_AUTO = "pex_auto_change"
    const val GROUP_RECOMMENDATIONS = "pex_recommendations"
    const val GROUP_INFO = "pex_info"

    // Messages
    const val COULD_NOT_REFRESH = "Could not refresh"

    // WORK
    const val WORK_AUTO_WALLPAPER = "work_auto_wallpaper"
    const val WORK_AUTO_WALLPAPER_NAME = "work_auto_wallpaper_name"

    const val WORK_DOWNLOAD_WALLPAPER = "work_download_wallpaper"
    const val WORK_DOWNLOAD_WALLPAPER_NAME = "work_download_wallpaper_name"

    const val WORK_RESTORE_WALLPAPER = "work_restore_wallpaper"
    const val WORK_RESTORE_WALLPAPER_NAME = "work_restore_wallpaper_name"

    const val WORKER_AUTO_WALLPAPER_IMAGE_URL_FULL = "WallpaperUrlFull"
    const val WORKER_AUTO_WALLPAPER_NOTIFICATION_IMAGE = "WallpaperUrlTiny"

    const val WALLPAPER_ID = "wallpaper_id"
    const val WALLPAPER_IMAGE_URL = "wallpaper_image_url"
    const val WALLPAPER_KEY = "wallpaper_key"

    // SHARING
    const val SUPPORT_EMAIL = "mailto:adrianwitaszak@gmial.com"
    const val DATA_TYPE = "image/*"

    // NOTIFICATIONS
    const val WALLPAPER_REQUEST_CODE = 2019
    const val WALLPAPER_GROUP_ID = "wallpaper_group"
    const val APP_GROUP_ID = "app_group"
    const val ACTION_AUTO = "action_auto"

    const val CHANNEL_AUTO = "pex_auto_wallpaper"
    const val CHANNEL_RECOMMENDATIONS = "pex_recommendations"
    const val CHANNEL_INFO = "pex_info"

    const val NOTIFICATION_WORK = "appName_notification_work"
    const val RESTORE_AUTO_WALLPAPER = "restore_auto_wallpaper"

    const val STORAGE_REQUEST_CODE = 233

    // IMAGE
    const val DIR_IMAGES = "images"
    const val DIR_RELATIVE_PATH = "Pictures/pex_wallpapers"
    const val MIME_TYPE = "image/jpeg"
    const val DISPLAY_NAME = "img_"
    const val IMAGE_FILE_EXT = ".jpg"

    const val BACKUP_WALLPAPER = "backup_wallpaper_"
}