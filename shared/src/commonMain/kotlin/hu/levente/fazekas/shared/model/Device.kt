package hu.levente.fazekas.shared.model

import kotlinx.serialization.Serializable

@Serializable
data class Device(
    val id: Int,
    val name: String,
    val desc: String
)
