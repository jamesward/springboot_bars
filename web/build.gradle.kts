plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
    id("dev.kilua")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
kotlin {
    wasmJs {
        useEsModules()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled = true
                }
                outputFileName = "web.js"
            }
        }
        binaries.executable()
        compilerOptions {
            target.set("es2015")
            freeCompilerArgs.add("-Xpartial-linkage-loglevel=ERROR")
        }
    }

    sourceSets {
        wasmJsMain.dependencies {
            implementation(project(":shared"))
            implementation("dev.kilua:kilua:0.0.35")
            implementation("dev.kilua:kilua-rpc-core:0.0.45")
        }
        wasmJsTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}
