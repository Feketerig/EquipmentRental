plugins {
    kotlin("js")
    kotlin("plugin.serialization")
}

group = "hu.levente.fazekas"
version = "1.0-SNAPSHOT"

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.2.0-pre.354")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.2.0-pre.354")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.9.3-pre.354")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:6.3.0-pre.354")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-redux:4.1.2-pre.354")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-redux:7.2.6-pre.354")
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
}