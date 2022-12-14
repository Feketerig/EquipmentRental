package hu.levente.fazekas.androidApp.presentation.camera

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asImageBitmap
import hu.levente.fazekas.androidApp.presentation.camera.QRCodeGenerator

@Composable
fun QRCodeView(str: String) {
    val generator by remember { mutableStateOf(QRCodeGenerator()) }
    val bitmap by remember { mutableStateOf(generator.encodeAsBitmap(str)) }
    Image(bitmap = bitmap!!.asImageBitmap(), contentDescription = "User QR code")
}