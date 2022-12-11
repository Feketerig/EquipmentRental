package hu.levente.fazekas.androidApp.presentation.devicecreation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.android.eszkozkolcsonzo.presentation.devicecreation.CreateDeviceState
import hu.levente.fazekas.shared.data.DeviceRepository
import hu.levente.fazekas.shared.model.Device
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CreateDeviceViewModel constructor(
    val deviceRepository: DeviceRepository
): ViewModel() {
    var state by mutableStateOf(CreateDeviceState())

    private val validationEventChannel = Channel<CreateDeviceEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun submit(){
        if (state.deviceName.isEmpty()){
            state = state.copy(deviceNameError = "Nem lehet üres")
            return
        }
        if (state.deviceDesc.isEmpty()){
            state = state.copy(deviceDescError = "Nem lehet üres")
            return
        }

        viewModelScope.launch {
            deviceRepository.addDevice(Device(id = 1, name = state.deviceName, desc = state.deviceDesc))
            validationEventChannel.send(CreateDeviceEvent.Succes)
        }
    }

    fun onNameChange(name: String){
        state = state.copy(deviceName = name)
    }
    fun onDescChange(desc: String){
        state = state.copy(deviceDesc = desc)
    }

    sealed class CreateDeviceEvent{
        object Succes: CreateDeviceEvent()
    }
}