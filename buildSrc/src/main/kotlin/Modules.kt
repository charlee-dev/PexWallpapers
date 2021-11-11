// "Module" means "subproject" in terminology of Gradle API.
// To be specific each "Android module" is a Gradle "subproject"
@Suppress("unused")
object Modules {

    const val APP = ":app"
    const val CORE = ":core"
    const val COMPONENTS = ":components"
    const val TEST_SHARED = ":test-shared"

    const val PEX = ":pex"
    const val DOMAIN = ":pex:domain"
    const val DATASOURCE = ":pex:datasource"
    const val INTERACTORS = ":pex:interactors"

    const val HOME = ":feature_home"
    const val PREVIEW = ":feature_preview"
    const val SEARCH = ":feature_search"
    const val FAVORITES = ":feature_favorites"
    const val SETTINGS = ":feature_settings"
}