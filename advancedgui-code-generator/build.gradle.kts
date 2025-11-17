dependencies {
    implementation("com.palantir.javapoet:javapoet:0.7.0")

    testImplementation("com.google.testing.compile:compile-testing:0.23.0")
    testImplementation(project(":advancedgui-api"))
}