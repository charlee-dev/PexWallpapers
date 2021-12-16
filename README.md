![visitors](https://visitor-badge.laobi.icu/badge?page_id=adrianwitaszak)
![](https://img.shields.io/github/stars/adrianwitaszak/pexwallpapers)
![](https://img.shields.io/github/forks/adrianwitaszak/pexwallpapers)
![](https://img.shields.io/github/watchers/adrianwitaszak/pexwallpapers)
![](https://img.shields.io/github/commit-activity/m/adrianwitaszak/pexwallpapers)
![](https://img.shields.io/github/last-commit/adrianwitaszak/pexwallpapers)
![](https://img.shields.io/github/repo-size/adrianwitaszak/pexwallpapers)
![](https://img.shields.io/tokei/lines/github/adrianwitaszak/pexwallpapers)
![](https://img.shields.io/github/languages/count/adrianwitaszak/pexwallpapers)
![](https://img.shields.io/github/languages/top/adrianwitaszak/pexwallpapers)

# Welcome to PexWallpapers!

Hi! PexWallpapers is one of my current projects in Jetpack Compose. 
The app use Pexels.com image library to showpictures/wallpapers in the app. 
You can browse different categories or just use **Search** to findsome beautiful wallpapers for your phone. 
If you add wallpapers to favourites than you can turn on **Auto Wallpaper Setter** in Settings, 
to have your phone wallpaper changed every specific periodof time.

# Getting started

To run app:

1. git clone repo to Android studio
2. Get your own Api Key from [Pexels.com](https://www.pexels.com/api/)
3. Add your own Api Key to gradle.properties in this format

   pex_api_access_key="234f9170000324234012343d044b1a3482ba588"

4. And run in emulator or on physical device

## Tech Stack

- 100% [Kotlin](https://kotlinlang.org/)
- [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) architecture
- [Retrofit](https://github.com/square/retrofit) for pulling data from network
- [Room](https://developer.android.com/training/data-storage/room) for persistence
- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) for threading
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) handles logic between UI and *Repository* using **Flows** and **Coroutines**
- [Coil](https://github.com/coil-kt/coil) for fetching images from web
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency injection
- [Jetpack Compose](https://developer.android.com/jetpack/compose) latest reactive ui by Google
- [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) for displaying long Wallpaper lists from web
- [Accompanist](https://github.com/google/accompanist) is used in a SwipeRefreshLayout
- [Shimmer](https://github.com/valentinilk/compose-shimmer) effect for while loading Wallpapers
- **UI testing** and **Unit testing** (in progress)
