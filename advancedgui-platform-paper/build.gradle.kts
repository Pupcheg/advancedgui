plugins {
    alias(libs.plugins.paperweight)
}

dependencies {
    api(project(":advancedgui-api"))
    api(project(":advancedgui-api-loader"))

    paperweight.paperDevBundle("${libs.versions.minecraft.get()}-R0.1-SNAPSHOT")
}