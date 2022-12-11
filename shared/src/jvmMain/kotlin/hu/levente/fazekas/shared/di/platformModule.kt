package hu.levente.fazekas.shared.di


import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import hu.levente.fazekas.database.DeviceDataBase
import io.ktor.client.engine.java.*
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
            .also { driver -> DeviceDataBase.Schema.create(driver) }
        DeviceDataBase(driver)
    }
    single { Java.create() }
}