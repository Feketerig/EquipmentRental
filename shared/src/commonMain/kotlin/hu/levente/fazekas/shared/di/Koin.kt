package hu.levente.fazekas.shared.di

import hu.levente.fazekas.shared.data.*
import hu.levente.fazekas.shared.network.Network
import hu.levente.fazekas.shared.network.NetworkInterface
import hu.levente.fazekas.shared.network.NewTokenResponseDTO
import hu.levente.fazekas.shared.use_case.ValidateEmail
import hu.levente.fazekas.shared.use_case.ValidatePassword
import hu.levente.fazekas.shared.use_case.ValidateRepeatedPassword
import hu.levente.fazekas.shared.use_case.ValidateTerms
import hu.levente.fazekas.shared.utils.hash.sha256
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule(), useCaseModule(), platformModule())
    }

fun commonModule() = module {
    //Network
    single { createHttpClient(get()) }
    single<NetworkInterface>{ Network(get()) }

    //Database
    single<DeviceDataSource> { SqlDelightDeviceDataSource(get()) }

    //Repository
    single<DeviceRepository> { DeviceRepositoryImpl(get(), get())}
    single<ReservationRepository> { ReservationRepositoryImpl(get()) }
}

fun useCaseModule() = module {
    single {ValidateEmail()}
    single {ValidatePassword()}
    single {ValidateRepeatedPassword()}
    single {ValidateTerms()}
}

fun createHttpClient(httpClientEngine: HttpClientEngine) = HttpClient(httpClientEngine) {
    install(Auth){
        bearer {
            loadTokens {
                Network.bearerTokenStorage.last()
            }
            refreshTokens {
                val refreshTokenInfo: NewTokenResponseDTO = client.post(NetworkInterface.Endpoints.User.url + "/login"){
                    setBody("${Network.email}|${Network.password.sha256()}")
                    markAsRefreshTokenRequest()
                }.body()
                Network.bearerTokenStorage.add(BearerTokens(refreshTokenInfo.accessToken, refreshTokenInfo.refreshToken))
                Network.bearerTokenStorage.last()
            }
            sendWithoutRequest { request ->
                request.url.host == NetworkInterface.Endpoints.User.url + "/login"
            }
        }
    }
    install(ContentNegotiation) {
        json()
    }
    install(Logging) {
        level = LogLevel.HEADERS
        logger = object : Logger {
            override fun log(message: String) {
                Napier.v(tag = "JvmHttpClient", message = message)
            }
        }
    }
    defaultRequest {
        contentType(ContentType.Application.Json)
        accept(ContentType.Application.Json)
    }
}