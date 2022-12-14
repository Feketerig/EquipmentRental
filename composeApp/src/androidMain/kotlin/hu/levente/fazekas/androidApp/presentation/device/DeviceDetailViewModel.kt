package hu.levente.fazekas.androidApp.presentation.device

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.levente.fazekas.shared.data.DeviceRepository
import hu.levente.fazekas.shared.data.ReservationRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DeviceDetailViewModel constructor(
    private val deviceRepository: DeviceRepository,
    private val reservationRepository: ReservationRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    var state by mutableStateOf(DeviceDetailState())

    private val validationEventChannel = Channel<DetailEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            val id = savedStateHandle.get<Int>("id") ?: return@launch
            state = state.copy(isLoading = true)
            state = state.copy(device = deviceRepository.getDevice(id))
            state = state.copy(isLoading = false)
        }
    }

    fun setStartDate(date: Long){
        state = state.copy(startDate = date)
    }

    fun setEndDate(date: Long){
        state = state.copy(endDate = date)
    }

    fun reserve(){
        if (state.startDate == null){
            state = state.copy(error = "A kezdődátum nincs megadva")
            return
        }
        if (state.endDate == null){
            state = state.copy(error = "A végdátum nincs megadva")
            return
        }
        if (state.startDate!! > state.endDate!!){
            state = state.copy(error = "A kezdődátum nem lehet előrébb a végdátumnál")
            return
        }
        /*if (MainViewModel.state.token == null){
            state = state.copy(error = "Nem vagy bejelentkezve")
            return
        }*/
        viewModelScope.launch {
            reservationRepository.addReservation(
                deviceId = savedStateHandle.get<Int>("id") ?: 0,
                startDate = state.startDate!!,
                endDate = state.endDate!!,
            )
            validationEventChannel.send(DetailEvent.Success)
        }
    }

    fun resetError(){
        state = state.copy(error = null)
    }

    sealed class DetailEvent{
        object Success: DetailEvent()
    }
}
