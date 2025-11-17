dependencies {
    api(libs.adventure.api)
    api(libs.guava)

    compileOnly(project(":advancedgui-code-generator"))
    annotationProcessor(project(":advancedgui-code-generator"))
}