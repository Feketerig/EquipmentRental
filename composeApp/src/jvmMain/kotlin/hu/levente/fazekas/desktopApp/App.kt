package hu.levente.fazekas.desktopApp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import hu.levente.fazekas.commonCompose.device.DeviceDetail
import hu.levente.fazekas.commonCompose.devicelist.DeviceListScreen
import hu.levente.fazekas.shared.data.DeviceRepository
import hu.levente.fazekas.shared.di.initKoin
import hu.levente.fazekas.shared.model.Device
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private val koin = initKoin().koin
fun main() = application {
    val windowState = rememberWindowState()

    var selectedDevice by remember { mutableStateOf<Device?>(null) }
    var devices by remember { mutableStateOf(emptyList<Device>()) }
    val deviceRepository = koin.get<DeviceRepository>()

    LaunchedEffect(true){
        deviceRepository.getDevices(query = "", forceRefresh = true).collect {
            devices = it
        }
    }

    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "EquipmentRental"
    ) {

        Row(Modifier.fillMaxSize()) {
            Box(Modifier.fillMaxWidth(0.3f).fillMaxHeight().background(color = Color.LightGray)) {
                DeviceListScreen(
                    devices = devices,
                    selectedDevice = selectedDevice,
                    onSelect = { selectedDevice = it },
                    onSearchQueryChange = {query, forceRefresh ->
                        GlobalScope.launch {
                            deviceRepository.getDevices(query = query, forceRefresh = forceRefresh)
                                .collectLatest {
                                devices = it
                            }
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.width(1.dp).fillMaxHeight())
            Box(Modifier.fillMaxHeight()) {
                selectedDevice?.let {
                    DeviceDetail(it)
                }
            }
        }
    }
}