package hu.levente.fazekas.shared.data

import hu.levente.fazekas.shared.model.ReservationInfo
import hu.levente.fazekas.shared.network.NetworkInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class ReservationRepositoryImpl(
    private val network: NetworkInterface
    ) : ReservationRepository {

    override suspend fun addReservation(deviceId: Int, startDate: Long, endDate: Long) {
        network.addReservation(deviceId, startDate, endDate)
    }

    override suspend fun getReservations(userId: Int?): Flow<List<ReservationInfo>> {
       return flow {
           if(userId == null) {
               val result = try {
                   network.getAllReservations()
               }catch (e: Exception){
                   null
               }
               result?.let {
                   val resultInfo = it.map {
                       val deviceName = network.getDevice(it.deviceId)?.name ?: ""
                       val userName = network.getUserNameById(it.userId)
                       val startDate = Instant.fromEpochMilliseconds(it.startDate).toLocalDateTime(TimeZone.currentSystemDefault())
                       val endDate = Instant.fromEpochMilliseconds(it.endDate).toLocalDateTime(TimeZone.currentSystemDefault())
                       val startDateText = "${startDate.year}-${startDate.month.ordinal+1}-${startDate.dayOfMonth}"
                       val endDateText = "${endDate.year}-${endDate.month.ordinal+1}-${endDate.dayOfMonth}"
                       ReservationInfo(
                           deviceName = deviceName,
                           userName = userName,
                           startDate = startDateText,
                           endDate = endDateText
                       )
                   }
                   emit(resultInfo)
               }
           }else{
               val result = try {
                   network.getAllReservationByUserId(userId)
               }catch (e: Exception){
                   null
               }
               result?.let {
                   val resultInfo = it.map {
                       val deviceName = network.getDevice(it.deviceId)?.name ?: ""
                       val userName = network.getUserNameById(it.userId)
                       val startDate = Instant.fromEpochMilliseconds(it.startDate).toLocalDateTime(TimeZone.currentSystemDefault())
                       val endDate = Instant.fromEpochMilliseconds(it.endDate).toLocalDateTime(TimeZone.currentSystemDefault())
                       val startDateText = "${startDate.year}-${startDate.month.ordinal+1}-${startDate.dayOfMonth}"
                       val endDateText = "${endDate.year}-${endDate.month.ordinal+1}-${endDate.dayOfMonth}"
                       ReservationInfo(
                           deviceName = deviceName,
                           userName = userName,
                           startDate = startDateText,
                           endDate = endDateText
                       )
                   }
                   emit(resultInfo)
               }
           }
       }
    }
}
