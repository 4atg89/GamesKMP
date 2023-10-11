import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

class UIConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        val libs = target.extensions.getByType<VersionCatalogsExtension>().named("libs")
        target.pluginManager.apply {
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
                        implementation(project(":core:common"))
                        implementation(project(":core:base"))
                    }
                }
            }
        }

    }
}