package hu.levente.fazekas.commonCompose.device

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.levente.fazekas.shared.model.Device

@Composable
fun DeviceDetail(
    device: Device
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /*Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = state.device?.name ?: "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp)
            )*/
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = device.name,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = device.desc)
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { /*viewModel.reserve()*/ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Foglalás")
        }
    }
}