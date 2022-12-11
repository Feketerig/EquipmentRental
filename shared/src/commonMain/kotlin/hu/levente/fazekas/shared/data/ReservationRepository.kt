package hu.levente.fazekas.shared.data

import hu.levente.fazekas.shared.model.ReservationInfo
import kotlinx.coroutines.flow.Flow

interface ReservationRepository {

    suspend fun addReservation(deviceId: Int, startDate: Long, endDate: Long)

    suspend fun getReservations(userId: Int?): Flow<List<ReservationInfo>>
}