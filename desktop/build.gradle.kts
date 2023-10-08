@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("jvm")
    alias(libs.plugins.jetbrains.compose)
    application
}

dependencies {
    implementation(project(":enter"))
    implementation(libs.precompose.core)
    implementation(compose.desktop.currentOs)
    implementation(libs.bundles.di.kotlin)
}

application {
    mainClass.set("desktop")
}

compose {
    kotlinCompilerPlugin.set("1.5.2")
    kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=1.9.10")
}