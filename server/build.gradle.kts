import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    application
}

group = "hu.levente.fazekas"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.ktor:ktor-server-netty:2.0.1")
    implementation("io.ktor:ktor-server-html-builder-jvm:2.0.1")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("hu.levente.fazekas.server.ServerKt")
}