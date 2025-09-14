plugins {
    alias(libs.plugins.fabric.loom)
}

var minecraftVersion: String = libs.versions.minecraft.get()
var fabricLoaderVersion: String = libs.versions.fabric.loader.get()

base {
    archivesName = "advancedgui-fabric-support"
}

dependencies {
    api(project(":advancedgui-api"))
    api(project(":advancedgui-api-loader"))

    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())
    modImplementation(libs.bundles.fabric)
}

tasks.withType<ProcessResources> {
    inputs.property("version", version)
    inputs.property("minecraft_version", minecraftVersion)
    inputs.property("loader_version", fabricLoaderVersion)

    filteringCharset = "UTF-8"

    filesMatching("fabric.mod.json") {
        expand(
            "version" to version,
            "minecraft_version" to minecraftVersion,
            "loader_version" to fabricLoaderVersion
        )
    }
}