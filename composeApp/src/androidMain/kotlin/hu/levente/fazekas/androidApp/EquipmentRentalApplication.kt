package hu.levente.fazekas.androidApp

import android.app.Application
import hu.levente.fazekas.androidApp.di.appModule
import hu.levente.fazekas.shared.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class EquipmentRentalApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger()
            androidContext(this@EquipmentRentalApplication)
            modules(appModule)
        }
    }
}