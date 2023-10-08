@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.google.ksp) version libs.versions.kspVersion
    alias(libs.plugins.android.library)
    alias(libs.plugins.ui.compose)
    alias(libs.plugins.kotlin.multiplatform)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    sourceSets {
        val androidMain by getting
        val iosMain by getting
        val desktopMain by getting

        val commonMain by getting {
//            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
            dependencies {
//                implementation(project(":ui:games"))
//                implementation(project(":ui:details"))
//                implementation(project(":data:games"))
                implementation(libs.coroutines.core)
                implementation(libs.kamel.image)
                implementation(libs.bundles.di.kotlin)

            }
        }
    }
}