package hu.levente.fazekas.server.routing

import hu.levente.fazekas.shared.utils.path.AppPath
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*

fun Route.pages() {
    route("/") {
        get(getDefaultPage)
        get(AppPath.devices + "/{...}", getDefaultPage)
        get(AppPath.leases + "/{...}", getDefaultPage)
        get(AppPath.reservations + "/{...}", getDefaultPage)
        get(AppPath.login + "/{...}", getDefaultPage)
        get(AppPath.register + "/{...}", getDefaultPage)
    }
}

val getDefaultPage: suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {
    call.respondText(
        this::class.java.classLoader.getResource("index.html")!!.readText(),
        ContentType.Text.Html
    )
}