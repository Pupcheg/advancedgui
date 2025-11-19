plugins {
    `java-library`
    alias(libs.plugins.lombok) apply false
}

allprojects {
    apply<JavaLibraryPlugin>()
    apply<io.freefair.gradle.plugins.lombok.LombokPlugin>()

    group = "me.supcheg.advancedgui"
    version = "1.0.0"

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
    }

    dependencies {
        api(rootProject.libs.checkerframework.qual)
        api(rootProject.libs.jetbrains.annotations)

        testImplementation(rootProject.libs.junit.jupiter.api)
        testImplementation(rootProject.libs.junit.jupiter.params)
        testImplementation(rootProject.libs.mockito.junit.jupiter)
        testImplementation(rootProject.libs.assertj.core)

        testRuntimeOnly(rootProject.libs.junit.platform.launcher)
        testRuntimeOnly(rootProject.libs.junit.jupiter.engine)
        testRuntimeOnly(rootProject.libs.slf4j.simple)
    }

    tasks {
        test {
            useJUnitPlatform()
        }
    }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }
}