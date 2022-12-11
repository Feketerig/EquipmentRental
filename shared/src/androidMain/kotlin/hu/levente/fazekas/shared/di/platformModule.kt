package hu.levente.fazekas.shared.di

import com.squareup.sqldelight.android.AndroidSqliteDriver
import hu.levente.fazekas.database.DeviceDataBase
import io.ktor.client.engine.android.*
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = AndroidSqliteDriver(DeviceDataBase.Schema, get(), "device.db")
        DeviceDataBase(driver)
    }
    single { Android.create()}
}