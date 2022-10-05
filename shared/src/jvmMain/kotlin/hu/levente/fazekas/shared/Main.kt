package hu.levente.fazekas.shared

import hu.levente.fazekas.shared.network.Network
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import java.util.concurrent.TimeUnit
import io.github.aakira.napier.Napier

fun Network.Companion.create() = HttpClient(OkHttp) {
    engine {
        config {
            retryOnConnectionFailure(true)
            connectTimeout(5, TimeUnit.SECONDS)
        }
    }
    install(JsonFeature) {
        serializer = KotlinxSerializer()
    }
    install(Logging) {
        level = LogLevel.HEADERS
        logger = object : Logger {
            override fun log(message: String) {
                Napier.v(tag = "JvmHttpClient", message = message)
            }
        }
    }
}