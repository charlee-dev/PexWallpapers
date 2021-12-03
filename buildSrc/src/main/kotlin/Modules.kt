// "Module" means "subproject" in terminology of Gradle API.
// To be specific each "Android module" is a Gradle "subproject"
@Suppress("unused")
object Modules {

    const val APP = ":app"
    const val BASE = ":base"
    const val COMPONENTS = ":components"
    const val CORE = ":core"
    const val DATA = ":data"
    const val DOMAIN = ":domain"
    const val TEST_UTILS = ":test_utils"

    // Features
    const val FEATURE_HOME = ":feature_home"
    const val FEATURE_PREVIEW = ":feature_preview"
    const val FEATURE_SEARCH = ":feature_search"
    const val FEATURE_FAVORITES = ":feature_favorites"
    const val FEATURE_SETTINGS = ":feature_settings"

    const val FEATURE_IMAGE = ":feature_image"
    const val FEATURE_AUTOMATION = ":feature_automation"
}