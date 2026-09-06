plugins {
    base
    kotlin("multiplatform") version "2.4.0" apply false
    kotlin("jvm") version "2.4.0" apply false
    kotlin("plugin.serialization") version "2.4.0" apply false
    kotlin("plugin.spring") version "2.4.0" apply false
    kotlin("plugin.power-assert") version "2.4.0" apply false
    kotlin("plugin.compose") version "2.4.0" apply false
    id("org.jetbrains.compose") version "1.12.0-alpha01" apply false
    id("com.google.devtools.ksp") version "2.3.9" apply false
    id("dev.kilua") version "0.0.35" apply false
    id("dev.kilua.rpc") version "0.0.45" apply false
    id("org.springframework.boot") version "4.1.1" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
}

allprojects {
    group = "bars"
    version = "1.0.0-SNAPSHOT"
}
