// "Module" means "subproject" in terminology of Gradle API.
// To be specific each "Android module" is a Gradle "subproject"
@Suppress("unused")
object Modules {

    const val APP = ":app"
    const val COMMON = ":common"
    const val COMPONENTS = ":components"
    const val SHARED = ":shared"

    // Datasource
    const val DOMAIN = ":datasource:domain"
    const val DATA = ":datasource:data"
    const val REPOSITORY = ":datasource:repository"

    const val DATASOURCE_SETTINGS = ":datasource_settings"


    // features
    const val FEATURE_HOME = ":feature_home"
    const val FEATURE_PREVIEW = ":feature_preview"
    const val FEATURE_SEARCH = ":feature_search"
    const val FEATURE_FAVORITES = ":feature_favorites"
    const val FEATURE_SETTINGS = ":feature_settings"
}