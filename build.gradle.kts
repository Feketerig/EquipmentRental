plugins{
    id("com.android.application").version("7.2.0").apply(false)
    id("com.android.library").version("7.2.0").apply(false)
    kotlin("android").version("1.6.10").apply(false)
    kotlin("multiplatform").version("1.6.10").apply(false)
    kotlin("plugin.serialization").version("1.6.10").apply(false)
    kotlin("kapt").version("1.6.10").apply(false)
    id("org.jetbrains.compose").version("1.1.1").apply(false)
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

group = "hu.levente.fazekas"
version = "1.0-SNAPSHOT"