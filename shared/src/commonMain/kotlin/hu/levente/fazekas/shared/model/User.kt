package hu.levente.fazekas.shared.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val password_hash: String,
    val privilege: Privilege,
){
    @Serializable
    enum class Privilege{
        Admin, Handler, User
    }
}
