plugins {
    kotlin("multiplatform")
    id("com.google.devtools.ksp")
}

kotlin {

    jvm {
        val main by compilations.getting {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    sourceSets {

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation(project(":annotations"))
                implementation("com.google.devtools.ksp:symbol-processing-api:1.9.10-1.0.13")
                //code generation
                //todo add it via libs
                val kotlinpoetVersion = "1.8.0"
                implementation("com.squareup:kotlinpoet:$kotlinpoetVersion")
                implementation("com.squareup:kotlinpoet-metadata:$kotlinpoetVersion")
                implementation("com.squareup:kotlinpoet-metadata-specs:$kotlinpoetVersion")
                implementation("com.squareup:kotlinpoet-classinspector-elements:$kotlinpoetVersion")

            }
        }

    }
}