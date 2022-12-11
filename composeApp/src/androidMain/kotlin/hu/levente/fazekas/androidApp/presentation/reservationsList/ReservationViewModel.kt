package hu.levente.fazekas.androidApp.presentation.reservationsList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.levente.fazekas.shared.data.ReservationRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ReservationViewModel constructor(
    val repository: ReservationRepository
): ViewModel() {
    var state by mutableStateOf(ReservationListState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            /*if (MainViewModel.state.token == null){
                validationEventChannel.send(ValidationEvent.NotLoggedIn)
                return@launch
            }*/
            getReservations(/*MainViewModel.state.id*/1)
        }
    }

    fun getReservations(
        id: Int?
    ){
        viewModelScope.launch {
            repository.getReservations(id)
                .collect { result ->
                    state = state.copy(reservations = result)
                }
        }
    }

    fun isAdmin(): Boolean{
        return true//MainViewModel.state.privilege == User.Privilege.Admin
    }

    sealed class ValidationEvent {
        object NotLoggedIn: ValidationEvent()
    }
}