import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    application
    kotlin("plugin.serialization")
}

group = "hu.levente.fazekas"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

dependencies {
    implementation(project(":shared"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")

    implementation("io.ktor:ktor-server-core:2.1.3")
    implementation("io.ktor:ktor-server-netty:2.1.3")
    implementation("io.ktor:ktor-server-content-negotiation:2.1.3")
    implementation("io.ktor:ktor-server-cors:2.1.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.1.3")
    implementation("io.ktor:ktor-server-auth:2.1.3")
    implementation("io.ktor:ktor-server-auth-jwt:2.1.3")

    implementation("ch.qos.logback:logback-classic:1.2.10")

    implementation("org.litote.kmongo:kmongo-coroutine-serialization:4.5.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("hu.levente.fazekas.server.ServerKt")
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}