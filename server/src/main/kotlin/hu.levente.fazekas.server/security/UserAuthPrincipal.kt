package hu.levente.fazekas.server.security

import io.ktor.server.auth.*

data class UserAuthPrincipal(val id: Int, val name: String, val email: String, val privilege: String): Principal