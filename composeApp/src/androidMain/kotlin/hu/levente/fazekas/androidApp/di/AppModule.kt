package hu.levente.fazekas.androidApp.di

import hu.bme.aut.android.eszkozkolcsonzo.presentation.lease.LeaseViewModel
import hu.levente.fazekas.androidApp.presentation.device.DeviceDetailViewModel
import hu.levente.fazekas.androidApp.presentation.devicecreation.CreateDeviceViewModel
import hu.levente.fazekas.androidApp.presentation.devicelist.DeviceListViewModel
import hu.levente.fazekas.androidApp.presentation.login.LoginViewModel
import hu.levente.fazekas.androidApp.presentation.registration.RegistrationViewModel
import hu.levente.fazekas.androidApp.presentation.reservationsList.ReservationViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    viewModel { DeviceDetailViewModel(get(), get(), get())}
    viewModel { DeviceListViewModel(get()) }
    viewModel { LeaseViewModel(get(), get(), get())}
    viewModel { ReservationViewModel(get()) }
    viewModel { CreateDeviceViewModel(get()) }
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { RegistrationViewModel(get(), get(), get(), get(), get()) }
}