package hu.levente.fazekas.desktopApp

import androidx.compose.material.Text
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import hu.levente.fazekas.shared.create
import hu.levente.fazekas.shared.network.Network
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.awt.SystemColor.text

suspend fun main() = application {
    val network = Network(Network.create())
    var text = "alma"
    GlobalScope.launch {
        text = network.getAllDevices().toString()
    }
    Window(onCloseRequest = ::exitApplication) {
        Text(text = text)
    }
}