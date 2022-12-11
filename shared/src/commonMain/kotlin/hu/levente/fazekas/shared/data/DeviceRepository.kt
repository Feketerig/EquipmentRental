package hu.levente.fazekas.shared.data

import hu.levente.fazekas.shared.model.Device
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {

    fun getDevices(
        query: String,
        forceRefresh: Boolean = false
    ): Flow<List<Device>>

    suspend fun getDevice(
        id: Int
    ): Device

    suspend fun addDevice(
        device: Device
    ): Int
}