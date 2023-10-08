import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Action
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

class MultiplatformSetupConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val libs = target.extensions.getByType<VersionCatalogsExtension>().named("libs")
        target.pluginManager.apply {
            apply(libs.findPlugin("jetbrains-multiplatform").get().get().pluginId)
            apply(libs.findPlugin("android-library").get().get().pluginId)
        }
        val library = target.extensions.getByType<LibraryExtension>()
        library.compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        @OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
        target.kotlin {
            targetHierarchy.default()

            androidTarget {
                compilations.all {
                    kotlinOptions {
                        jvmTarget = "17"
                    }
                }
            }
            jvm("desktop") {
                compilations.all {
                    kotlinOptions.jvmTarget = "17"
                }
            }
            listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach {
                it.binaries.framework {
                    println("MultiplatformSetupConventionPlugin " + target.name)
                    baseName = target.name
                }
            }
        }

        target.androidLibrary {
            namespace = "com.example.${target.name}"
            compileSdk = 34
            defaultConfig {
                minSdk = 24
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }
    }

}

/**
 * Configures the [android][com.android.build.gradle.LibraryExtension] extension.
 */
private fun Project.`androidLibrary`(configure: Action<com.android.build.gradle.LibraryExtension>): Unit =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("android", configure)