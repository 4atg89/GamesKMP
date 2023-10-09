pluginManagement {
    includeBuild("build-common")
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    plugins {
        val kotlinVersion = "1.9.10"//extra["kotlin.version"] as String
        val agpVersion = "8.1.2"//extra["agp.version"] as String
        val composeVersion = "1.5.2"//extra["compose.version"] as String

        kotlin("jvm").version(kotlinVersion)
        kotlin("multiplatform").version(kotlinVersion)
        kotlin("android").version(kotlinVersion)
        kotlin("plugin.serialization").version(kotlinVersion)

        id("com.android.application").version(agpVersion)
        id("com.android.library").version(agpVersion)

        id("org.jetbrains.compose").version(composeVersion)
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "GamesKMP"
include(":androidApp")
include(":desktop")
include(":core:base")
include(":core:common")
include(":enter")
include(":ui:games")
include(":ui:details")
include(":data:games")
include(":data:network")