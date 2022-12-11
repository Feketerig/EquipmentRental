package hu.levente.fazekas.server

import hu.bme.aut.application.backend.utils.getDeviceBackend
import hu.bme.aut.application.backend.utils.getLeaseBackend
import hu.bme.aut.application.backend.utils.getReservationBackend
import hu.bme.aut.application.backend.utils.getUserBackend
import hu.bme.aut.application.database.MongoDB
import hu.levente.fazekas.server.security.configureSecurity
import hu.levente.fazekas.server.routing.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.*
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

fun main() {
    val mongoDB = MongoDB(database = KMongo.createClient().coroutine.getDatabase("eszkozkolcsonzo"))

    embeddedServer(Netty, 8080) {
        install(ContentNegotiation) {
            json()
        }
        install(CORS) {
           allowMethod(HttpMethod.Get)
           allowMethod(HttpMethod.Post)
           allowMethod(HttpMethod.Delete)
           anyHost()
        }
        configureSecurity()
        install(Routing) {
            deviceApi(getDeviceBackend(mongoDB))
            leaseApi(getLeaseBackend(mongoDB))
            reservationApi(getReservationBackend(mongoDB))
            userApi(getUserBackend(mongoDB))

            pages()

            static("/") {
                resources("")
            }
        }
    }.start(wait = true)
}