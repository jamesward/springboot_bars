import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.power-assert")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}


extra["kotlin-coroutines.version"] = "1.11.0"
kotlin {
    jvmToolchain(25)
}

dependencies {
    implementation(project(":shared"))
    implementation(kotlin("reflect"))
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.11.0")
    runtimeOnly("org.postgresql:r2dbc-postgresql")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:postgresql:1.20.4")
    testImplementation("org.testcontainers:r2dbc:1.20.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

springBoot {
    mainClass.set("bars.server.MainKt")
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootBuildImage>("bootBuildImage") {
    imageName.set("springboot_bars")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    testLogging {
        showStandardStreams = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        events(STARTED, PASSED, SKIPPED, FAILED)
    }
}

val productionWasm = gradle.startParameter.taskNames.any {
    val task = it.lowercase()
    "build" in task || "bootjar" in task || "bootbuildimage" in task
}

val copyWasmAssets = tasks.register<Sync>("copyWasmAssets") {
    if (productionWasm) {
        dependsOn(":web:wasmJsBrowserProductionWebpack")
        from(project(":web").layout.buildDirectory.dir("kotlin-webpack/wasmJs/productionExecutable"))
    } else {
        dependsOn(":web:wasmJsBrowserDevelopmentWebpack")
        from(project(":web").layout.buildDirectory.dir("kotlin-webpack/wasmJs/developmentExecutable"))
    }
    from(project(":web").projectDir.resolve("src/wasmJsMain/resources"))
    into(layout.buildDirectory.dir("resources/main/public"))
}

tasks.named("processResources") {
    dependsOn(copyWasmAssets)
}
