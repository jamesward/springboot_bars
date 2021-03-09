import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
    application
    kotlin("jvm") version "1.4.31"
    kotlin("plugin.spring") version "1.4.31"
    id("org.springframework.boot") version "2.4.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    //id("org.springframework.experimental.aot") version "0.9.0-SNAPSHOT"
}

repositories {
    mavenCentral()
    jcenter()
    maven(uri("https://repo.spring.io/snapshot"))
    maven(uri("https://repo.spring.io/milestone"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("io.r2dbc:r2dbc-postgresql")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    implementation("org.webjars:bootstrap:4.5.3")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")

    //implementation("org.springframework.experimental:spring-native:0.9.0-SNAPSHOT")
    implementation("org.springframework.experimental:spring-graalvm-native:0.8.5")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.postgresql:postgresql")
    testImplementation("org.testcontainers:postgresql:1.15.2")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("org.junit.vintage", "junit-vintage-engine")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        useIR = true
    }
}

tasks.withType<org.springframework.boot.gradle.tasks.run.BootRun> {
    dependsOn("testClasses")
    classpath = configurations["developmentOnly"] + sourceSets["test"].runtimeClasspath
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {
    if (project.hasProperty("native")) {
        val args = setOf(
            "-Dspring.spel.ignore=true",
            "-Dspring.native.remove-yaml-support=true",
            "--no-fallback"
        )
        builder = "paketobuildpacks/builder:tiny"
        environment = mapOf(
            "BP_BOOT_NATIVE_IMAGE" to "1",
            "BP_BOOT_NATIVE_IMAGE_BUILD_ARGUMENTS" to args.joinToString(" ")
        )
    }
}

tasks.withType<Test> {
    useJUnitPlatform()

    testLogging {
        showStandardStreams = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        events(STARTED, PASSED, SKIPPED, FAILED)
    }
}

application {
    mainClass.set("bars.MainKt")
}