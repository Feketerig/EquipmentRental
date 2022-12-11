package hu.levente.fazekas.androidApp.presentation.reservationsList

import hu.levente.fazekas.shared.model.ReservationInfo

data class ReservationListState(
    val reservations: List<ReservationInfo> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false
)