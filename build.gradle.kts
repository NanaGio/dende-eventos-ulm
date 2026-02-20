plugins {
    kotlin("jvm") version "2.3.0"
}

group = "org.oat1"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0")
}

kotlin {
    jvmToolchain(23)
}

tasks.test {
    useJUnitPlatform()
}