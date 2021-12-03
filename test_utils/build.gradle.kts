import dependencies.TestDependencies

plugins {
    java
    id(Plugins.KOTLIN)
}

dependencies {
    implementation(TestDependencies.test_runner)
    implementation(TestDependencies.coroutinesTest)
}