package hu.levente.fazekas.androidApp.presentation.lease

import hu.levente.fazekas.shared.model.Device
import hu.levente.fazekas.shared.model.Reservation
import hu.levente.fazekas.shared.model.User

data class LeaseState(
    val showCamera: Boolean = false,
    val reservation: Reservation? = null,
    val reservationUser: User? = null,
    val isDevice: Boolean = false,
    val deviceId: Int? = null,
    val device: Device? = null,
    val isUser: Boolean = false,
    val userId: Int? = null,
    val user: User? = null
)