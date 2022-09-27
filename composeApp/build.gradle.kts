plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
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
                implementation("androidx.activity:activity-compose:1.5.1")

                implementation("androidx.core:core-ktx:1.7.0")
                implementation("androidx.compose.ui:ui:1.1.1")
                implementation("androidx.compose.material:material:1.1.1")
                implementation("androidx.compose.ui:ui-tooling-preview:1.1.1")
                implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
                implementation("androidx.activity:activity-compose:1.4.0")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("org.jetbrains.compose.runtime:runtime:1.1.1")
                implementation("org.jetbrains.compose.foundation:foundation:1.1.1")
                implementation("org.jetbrains.compose.material:material:1.1.1")
                implementation("org.jetbrains.compose.desktop:desktop-jvm-windows-x64:1.1.1")
            }
        }
    }
}

android {
    compileSdk = 32
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
        targetSdk = 32
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
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