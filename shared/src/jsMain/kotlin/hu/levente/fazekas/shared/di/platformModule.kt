package hu.levente.fazekas.shared.di

import com.squareup.sqldelight.drivers.sqljs.initSqlDriver
import hu.levente.fazekas.database.DeviceDataBase
import io.ktor.client.engine.js.*
import kotlinx.coroutines.await
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = initSqlDriver(DeviceDataBase.Schema)
        driver.then { driver1 -> DeviceDataBase(driver1) }
    }
    single { Js.create() }
}