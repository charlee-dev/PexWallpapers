// "Module" means "subproject" in terminology of Gradle API.
// To be specific each "Android module" is a Gradle "subproject"
@Suppress("unused")
object Modules {

    const val APP = ":app"
    const val COMMON = ":common"
    const val COMPONENTS = ":components"
    const val TEST_SHARED = ":test-shared"

    const val DATASOURCE = ":datasource"
    const val DOMAIN = ":datasource:domain"
    const val DATA = ":datasource:data"
    const val REPOSITORY = ":datasource:repository"

    const val HOME = ":feature_home"
    const val PREVIEW = ":feature_preview"
    const val SEARCH = ":feature_search"
    const val FAVORITES = ":feature_favorites"
    const val SETTINGS = ":feature_settings"
}