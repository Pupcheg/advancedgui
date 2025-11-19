plugins {
    alias(libs.plugins.run.paper)
    alias(libs.plugins.resource.factory.paper)
    alias(libs.plugins.shadow)
}

var minecraftVersion: String = libs.versions.minecraft.get()

dependencies {
    api(project(":advancedgui-platform-paper"))

    compileOnly("io.papermc.paper:paper-api:$minecraftVersion-R0.1-SNAPSHOT")
}

tasks {
    shadowJar {
        dependencies {
            include(dependency("me.supcheg.advancedgui:.*"))
            include(dependency("org.spongepowered:configurate-jackson:.*"))
        }
    }

    runServer {
        minecraftVersion(minecraftVersion)
    }
}

configurations {
    paperPluginYaml {
        name = "testplugin"
        main = "me.supcheg.advancedgui.testplugin.TestPlugin"
        apiVersion = minecraftVersion
    }
}
