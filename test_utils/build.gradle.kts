import commons.api
import commons.implementation
import dependencies.TestDependencies

plugins {
    java
    id(Plugins.KOTLIN)
}

dependencies {
    implementation(TestDependencies.coroutinesTest)
    api(TestDependencies.test_runner)
}