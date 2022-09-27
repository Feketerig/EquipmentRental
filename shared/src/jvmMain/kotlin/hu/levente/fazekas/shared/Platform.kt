package hu.levente.fazekas.shared

actual class Platform actual constructor() {
    actual val platform: String
        get() = "Desktop"
}