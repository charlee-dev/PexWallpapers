import kotlin.reflect.full.memberProperties

// "Module" means "subproject" in terminology of Gradle API.
// To be specific each "Android module" is a Gradle "subproject"
@Suppress("unused")
object Modules {

    const val app = ":app"
    const val testShared = ":test-shared"
    const val base = ":base"

    const val pex = ":pex"
    const val data = ":pex:data"
    const val domain = ":pex:domain"
    const val usecases = ":pex:usecases"

    const val features = ":features"
    const val home = ":features:home"
    const val search = ":features:search"
    const val favorites = ":features:favorites"
    const val settings = ":features:settings"

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