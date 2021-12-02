import dependencies.TestDependencies

plugins {
    java
    id(Plugins.KOTLIN)
}

dependencies {
    testImplementation(TestDependencies.coroutinesTest)
    testImplementation(TestDependencies.test_runner)
}