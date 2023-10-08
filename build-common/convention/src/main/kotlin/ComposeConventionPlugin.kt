import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val libs = target.extensions.getByType<VersionCatalogsExtension>().named("libs")
        target.pluginManager.apply {
            apply(libs.findPlugin("jetbrains.compose").get().get().pluginId)
            apply(libs.findPlugin("jetbrains-multiplatform").get().get().pluginId)
        }
        target.extensions.getByType<LibraryExtension>().compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        target.kotlin {
            sourceSets {
                commonMain {
                    dependencies {
                        implementation(compose.runtime)
                        implementation(compose.foundation)
                        implementation(compose.material)
                        @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                        implementation(compose.components.resources)
                        implementation (libs.preCompose)
                    }
                }
            }
        }

    }
}

private val VersionCatalog.preCompose
    get() = findBundle("precompose").get()

private val KotlinMultiplatformExtension.compose: ComposePlugin.Dependencies
    get() = (this as ExtensionAware).extensions.getByName("compose") as ComposePlugin.Dependencies