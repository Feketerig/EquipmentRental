package hu.levente.fazekas.shared.network

@kotlinx.serialization.Serializable
data class NewTokenResponseDTO(
    val accessToken: String,
    val refreshToken: String,
)