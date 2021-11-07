import kotlin.reflect.full.memberProperties

// "Module" means "subproject" in terminology of Gradle API.
// To be specific each "Android module" is a Gradle "subproject"
@Suppress("unused")
object Modules {

    const val APP = ":app"
    const val CORE = ":core"
    const val TEST_SHARED = ":test-shared"

    const val PEX = ":pex"
    const val DATA = ":pex:data"
    const val DOMAIN = ":pex:domain"
    const val USECASES = ":pex:usecases"

    const val FEATURES = ":features"
    const val BASE = ":features:base"
    const val HOME = ":features:home"
    const val SEARCH = ":features:search"
    const val FAVORITES = ":features:favorites"
    const val SETTINGS = ":features:settings"

    // False positive" function can be private"
    // See: https://youtrack.jetbrains.com/issue/KT-33610
    /*
    Return list of all modules in the project
     */
    fun getAllModules() = Modules::class.memberProperties
        .filter { it.isConst }
        .map { it.getter.call().toString() }
        .toSet()

    /*
     Return list of feature modules in the project
     */
//    fun getFeatureModules(): Set<String> {
//        val featurePrefix = ":feature_"
//
//        return getAllModules()
//            .filter { it.startsWith(featurePrefix) }
//            .toSet()
//    }
}