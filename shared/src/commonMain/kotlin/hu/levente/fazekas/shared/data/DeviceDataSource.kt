package hu.levente.fazekas.shared.data

import hu.levente.fazekas.shared.model.Device

interface DeviceDataSource {
    suspend fun insertDevice(device: Device)
    suspend fun getDeviceById(id: Int): Device
    suspend fun getAllDevices(): List<Device>
    suspend fun clearDevices()
    suspend fun searchDevices(query: String): List<Device>
}