import dependencies.Dependencies

plugins {
    java
    id(Plugins.KOTLIN)
}

dependencies {
    implementation(Dependencies.coroutinesCore)
}