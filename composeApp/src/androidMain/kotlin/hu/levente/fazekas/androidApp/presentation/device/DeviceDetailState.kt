package hu.levente.fazekas.androidApp.presentation.device

import hu.levente.fazekas.shared.model.Device

data class DeviceDetailState(
    val device: Device? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val startDate: Long? = null,
    val endDate: Long? = null
)
