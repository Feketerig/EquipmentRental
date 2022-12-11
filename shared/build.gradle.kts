plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization")
    id("com.squareup.sqldelight")
}

group = "hu.levente.fazekas"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    android()
    js(IR) {
        useCommonJs()
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-serialization:2.1.3")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
                implementation("com.soywiz.korlibs.krypto:krypto:2.4.12")
                implementation("io.ktor:ktor-client-logging:2.1.3")
                implementation("io.ktor:ktor-client-auth:2.1.3")
                implementation("io.ktor:ktor-client-core:2.1.3")
                implementation("io.ktor:ktor-client-content-negotiation:2.1.3")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.1.3")
                implementation("io.github.aakira:napier:2.3.0")
                implementation("com.squareup.sqldelight:runtime:1.5.3")

                implementation("io.insert-koin:koin-core:3.2.2")

                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-java:2.1.3")
                implementation("com.squareup.sqldelight:sqlite-driver:1.5.3")
            }
        }
        val jvmTest by getting
        val androidMain by getting {
            dependencies {
                implementation("com.google.android.material:material:1.5.0")
                implementation("io.ktor:ktor-client-android:2.1.3")
                implementation("com.squareup.sqldelight:android-driver:1.5.3")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-js:2.1.3")
                implementation("com.squareup.sqldelight:sqljs-driver:1.5.3")
                //implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.0.0-pre.332-kotlin-1.6.21")
                //implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.0.0-pre.332-kotlin-1.6.21")
                //implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.9.0-pre.332-kotlin-1.6.21")
                //implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:6.3.0-pre.332-kotlin-1.6.21")
                //implementation("org.jetbrains.kotlin-wrappers:kotlin-redux:4.1.2-pre.332-kotlin-1.6.21")
                //implementation("org.jetbrains.kotlin-wrappers:kotlin-react-redux:7.2.6-pre.332-kotlin-1.6.21")
            }
        }
        val jsTest by getting
    }
}

android {
    compileSdkVersion(33)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(33)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

sqldelight{
    database("DeviceDataBase"){
        packageName = "hu.levente.fazekas.database"
        sourceFolders = listOf("sqldelight")
    }
}

buildscript{
    dependencies{
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.6.10")
    }
}