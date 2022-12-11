package hu.levente.fazekas.androidApp.presentation.devicelist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.levente.fazekas.shared.data.DeviceRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DeviceListViewModel(
    private val repository: DeviceRepository
) : ViewModel() {
    var state by mutableStateOf(DeviceListState())

    private var searchJob: Job? = null

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            if (false/*MainViewModel.state.token == null*/) {
                validationEventChannel.send(ValidationEvent.NotLoggedIn)
                return@launch
            }
            getDevices(true)
        }
    }

    fun getDevices(
        forceRefresh: Boolean = false,
        query: String = state.searchQuery.lowercase()
    ) {
        viewModelScope.launch {
            repository.getDevices(query, forceRefresh)
                .collect { result ->
                    state = state.copy(devices = result)

                }
        }
    }

    fun onSearchTextChange(newValue: String){
        state = state.copy(searchQuery = newValue)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            getDevices()
        }
    }

    fun onCheckedChange(newValue: Boolean){
        state = state.copy(onlyAvailable = newValue)
        getDevices(forceRefresh = true)
    }

    sealed class ValidationEvent {
        object NotLoggedIn: ValidationEvent()
    }
}