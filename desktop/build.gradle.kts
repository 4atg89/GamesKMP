plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    application
}

dependencies {
    implementation(project(":enter"))
    implementation("moe.tlaster:precompose:1.5.4")
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