import org.bouncycastle.crypto.tls.ConnectionEnd.client

plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose") version "1.2.0"
}

kotlin {
    android()
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":shared"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.compose.ui:ui:1.2.1")
                implementation("androidx.compose.ui:ui-tooling:1.2.1")
                implementation("androidx.compose.ui:ui-tooling-preview:1.2.1")
                implementation("androidx.compose.foundation:foundation:1.2.1")
                implementation("androidx.compose.material:material:1.2.1")
                implementation("androidx.activity:activity-compose:1.6.0")

                implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
                implementation("androidx.navigation:navigation-compose:2.6.0-alpha01")


                implementation("androidx.core:core-ktx:1.9.0")
                implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")

                implementation("com.google.accompanist:accompanist-swiperefresh:0.25.1")

                // CameraX
                implementation("androidx.camera:camera-camera2:1.2.0-beta02")
                implementation("androidx.camera:camera-lifecycle:1.2.0-beta02")
                implementation("androidx.camera:camera-view:1.2.0-beta02")

                // Zxing
                implementation("com.google.zxing:core:3.3.3")
                //Permissions
                implementation("com.google.accompanist:accompanist-permissions:0.21.1-beta")

                //Hash
                implementation("com.soywiz.korlibs.krypto:krypto-android:2.2.0")

                //JWT
                implementation("com.auth0.android:jwtdecode:2.0.1")

                implementation("io.insert-koin:koin-core:3.2.2")
                implementation("io.insert-koin:koin-android:3.3.0")
                implementation("io.insert-koin:koin-androidx-compose:3.3.0")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("org.jetbrains.compose.runtime:runtime:1.2.0-alpha01-dev683")
                implementation("org.jetbrains.compose.foundation:foundation:1.2.0-alpha01-dev683")
                implementation("org.jetbrains.compose.material:material:1.2.0-alpha01-dev683")
                implementation("org.jetbrains.compose.desktop:desktop-jvm-windows-x64:1.1.1")
                implementation("io.insert-koin:koin-core:3.2.2")
            }
        }
    }
}

android {
    compileSdk = 33
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

compose.desktop {
    application {
        mainClass = "hu.levente.fazekas.desktopApp.AppKt"
        nativeDistributions {
            targetFormats(org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg)
            packageName = "EquipmentRental"
            packageVersion = "1.0"
        }
    }
}