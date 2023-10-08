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
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
