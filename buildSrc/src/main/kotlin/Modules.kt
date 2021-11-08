// "Module" means "subproject" in terminology of Gradle API.
// To be specific each "Android module" is a Gradle "subproject"
@Suppress("unused")
object Modules {

    const val APP = ":app"
    const val CORE = ":core"
    const val BASE = ":base"
    const val COMPOSABLES = ":composables"
    const val DOMAIN = ":domain"
    const val TEST_SHARED = ":test-shared"

    const val PEX = ":pex"
    const val DATA = ":pex:data"
    const val USECASES = ":pex:usecases"

    const val HOME = ":feature_home"
//    const val SEARCH = ":feature_search"
//    const val FAVORITES = ":feature_favorites"
//    const val SETTINGS = ":feature_settings"
}