plugins {
    alias(libs.plugins.paperweight)
}

var minecraftVersion: String = libs.versions.minecraft.get()

dependencies {
    paperweight.paperDevBundle("$minecraftVersion-R0.1-SNAPSHOT")

    api(project(":advancedgui-api"))
    api(project(":advancedgui-api-loader"))
}