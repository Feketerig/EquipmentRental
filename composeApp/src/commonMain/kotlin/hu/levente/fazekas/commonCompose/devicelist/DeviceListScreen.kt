package hu.levente.fazekas.commonCompose.devicelist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.levente.fazekas.shared.model.Device
import hu.levente.fazekas.shared.utils.path.AppPath.devices


@Composable
fun DeviceListScreen(
    devices: List<Device>,
    selectedDevice: Device?,
    onSelect: (Device) -> Unit,
    onSearchQueryChange: (String, Boolean) -> Unit
) {
    var query by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            TextField(
                value = query,
                onValueChange = {
                    query = it
                    onSearchQueryChange(it, false)
                },
                placeholder = {
                    Text(text = "Keresés...")
                },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Column(
                modifier = Modifier.height(60.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { onSearchQueryChange(query, true) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Keresés", fontSize = 10.sp)
                }
                /*Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Checkbox(
                        checked = true,//state.onlyAvailable,
                        onCheckedChange = { *//*viewModel.onCheckedChange(it)*//* },
                        enabled = true
                    )
                    Text(
                        text = "Elérhető",
                        fontSize = 10.sp
                    )
                }*/
            }

        }
        Spacer(modifier = Modifier.height(10.dp))
        if (devices.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(devices.size) { index ->
                    val device = devices[index]
                    DeviceListItemCard(
                        name = device.name,
                        desc = device.desc,
                        selected = (device.id == selectedDevice?.id)
                    ) {
                        onSelect(device)
                    }
                }
            }
        }
    }
}