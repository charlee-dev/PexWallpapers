import kotlin.reflect.full.memberProperties

object Modules {

    const val app = ":app"
    const val core = ":data"
    const val domain = ":domain"
    const val base = ":base"
    const val testShared = ":test-shared"

    const val features = ":features"
    const val home = ":features:home"
    const val search = ":features:search"
    const val favorites = ":features:favorites"
    const val settings = ":features:settings"

    fun getAllModules() = Modules::class.memberProperties
        .filter { it.isConst }
        .map { it.getter.call().toString() }
        .toSet()
}