package hu.levente.fazekas.shared.data

import hu.levente.fazekas.shared.model.Device
import hu.levente.fazekas.shared.network.NetworkInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeviceRepositoryImpl(
    private val network: NetworkInterface,
    private val db: DeviceDataSource
    ): DeviceRepository {

    override fun getDevices(
        query: String,
        forceRefresh: Boolean,
    ): Flow<List<Device>> {
        return flow {
            val localDevices = searchDevices(query)
            emit(localDevices)


            val isDbEmpty = localDevices.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !forceRefresh
            if (shouldJustLoadFromCache) {
                return@flow
            }

            val remoteDevices = try {
                network.getAllDevices()
            } catch (e: Exception) {
                null
            }

            remoteDevices?.let { devices ->
                if (devices.isEmpty()) {
                    return@flow
                }
                db.clearDevices()
                devices.forEach {
                    db.insertDevice(it)
                }

                emit(searchDevices(query = query))
            }
        }
    }

    private suspend fun searchDevices(
        query: String
    ): List<Device> {
        return db.searchDevices(query = query)
    }

    override suspend fun getDevice(id: Int): Device {
        return db.getDeviceById(id)
    }

    override suspend fun addDevice(device: Device): Int {
        val id =  network.addDevice(device)
        db.insertDevice(
            device = Device(
                id = id,
                name = device.name,
                desc = device.desc
            )
        )
        return id
    }
}