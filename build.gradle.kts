import io.freefair.gradle.plugins.lombok.LombokPlugin

plugins {
    `java-library`
    alias(libs.plugins.lombok) apply false
}

allprojects {
    apply<JavaLibraryPlugin>()
    apply<LombokPlugin>()

    group = "me.supcheg.advancedgui"
    version = "1.0.0"

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://api.modrinth.com/maven")
    }

    dependencies {
        api(rootProject.libs.bundles.annotations)
        testImplementation(rootProject.libs.bundles.test)
        testRuntimeOnly(rootProject.libs.bundles.test.runtime)
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }
}