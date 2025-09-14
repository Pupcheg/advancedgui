
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml
import xyz.jpenilla.runpaper.task.RunServer

plugins {
    alias(libs.plugins.run.paper)
    alias(libs.plugins.resource.factory.paper)
    alias(libs.plugins.shadow)
}

var minecraftVersion: String = libs.versions.minecraft.get()

dependencies {
    api(project(":advancedgui-platform-paper"))

    compileOnly("io.papermc.paper:paper-api:${minecraftVersion}-R0.1-SNAPSHOT")
}

tasks.withType<ShadowJar> {
    dependencies {
        include(dependency("me.supcheg.advancedgui:.*"))
        include(dependency("org.spongepowered:configurate-jackson:.*"))
    }
}

tasks.withType<RunServer> {
    minecraftVersion(minecraftVersion)
}

configure<PaperPluginYaml> {
    name = "testplugin"
    main = "me.supcheg.advancedgui.testplugin.TestPlugin"
    apiVersion = minecraftVersion
}