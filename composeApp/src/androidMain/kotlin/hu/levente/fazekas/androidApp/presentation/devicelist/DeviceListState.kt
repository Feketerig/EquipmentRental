package hu.levente.fazekas.androidApp.presentation.devicelist

import hu.levente.fazekas.shared.model.Device

data class DeviceListState(
    val devices: List<Device> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val onlyAvailable: Boolean = true,
    val searchQuery: String = ""
)
