package hu.levente.fazekas.shared.data

import database.DeviceEntity
import hu.levente.fazekas.database.DeviceDataBase
import hu.levente.fazekas.shared.model.Device

class SqlDelightDeviceDataSource(db: DeviceDataBase): DeviceDataSource {

    private val queries = db.deviceQueries

    override suspend fun insertDevice(device: Device) {
        queries.insertDevice(
            id = device.id.toLong(),
            name = device.name,
            desc = device.desc
        )
    }

    override suspend fun getDeviceById(id: Int): Device {
        return queries
            .getDeviceById(id = id.toLong())
            .executeAsOne()
            .toDomain()
    }

    override suspend fun getAllDevices(): List<Device> {
        return queries.getAllDevices().executeAsList().map(DeviceEntity::toDomain)
    }

    override suspend fun clearDevices() {
        queries.clearDevices()
    }

    override suspend fun searchDevices(query: String): List<Device> {
        return queries.searchDevices(query).executeAsList().map(DeviceEntity::toDomain)
    }
}