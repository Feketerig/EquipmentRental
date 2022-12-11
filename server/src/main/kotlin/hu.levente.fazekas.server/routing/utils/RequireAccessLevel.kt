package hu.levente.fazekas.server.routing.utils

import hu.levente.fazekas.server.security.UserAuthPrincipal
import io.ktor.http.*
import io.ktor.util.pipeline.*
import hu.levente.fazekas.shared.model.User.Privilege
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*

suspend inline fun PipelineContext<Unit, ApplicationCall>.requireAccessLevel(privilege: Privilege, body: () -> Unit) {
    if (checkAccessLevel((call.authentication.principal as UserAuthPrincipal), privilege)) {
        body()
    }
    else {
        call.respond(HttpStatusCode.Forbidden)
    }
}

fun checkAccessLevel(principal: UserAuthPrincipal, required: Privilege): Boolean {
    return when (required) {
        Privilege.Admin -> Privilege.Admin.eq(principal.privilege)
        Privilege.Handler -> Privilege.Handler.eq(principal.privilege) || Privilege.Admin.eq(principal.privilege)
        Privilege.User -> true
    }
}

private fun Privilege.eq(other: String): Boolean = toString() == other