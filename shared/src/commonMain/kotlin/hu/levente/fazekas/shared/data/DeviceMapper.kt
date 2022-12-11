package hu.levente.fazekas.shared.data

import database.DeviceEntity
import hu.levente.fazekas.shared.model.Device

fun Device.toLocalDB(): DeviceEntity = DeviceEntity(
    id = id.toLong(),
    name = name,
    desc = desc
)

fun DeviceEntity.toDomain(): Device = Device(
    id = id.toInt(),
    name = name,
    desc = desc
)