package hu.levente.fazekas.desktopApp

import androidx.compose.material.Text
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        Text(text = "Hello World")
    }
}