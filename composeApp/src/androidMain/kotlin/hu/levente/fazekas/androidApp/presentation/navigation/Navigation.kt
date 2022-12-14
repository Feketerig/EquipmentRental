package hu.levente.fazekas.androidApp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import hu.bme.aut.android.eszkozkolcsonzo.presentation.navigation.Screen
import hu.levente.fazekas.androidApp.presentation.device.DeviceDetail
import hu.levente.fazekas.androidApp.presentation.devicecreation.CreateDevice
import hu.levente.fazekas.androidApp.presentation.devicelist.DeviceListScreen
import hu.levente.fazekas.androidApp.presentation.lease.LeaseScreen
import hu.levente.fazekas.androidApp.presentation.login.LoginScreen
import hu.levente.fazekas.androidApp.presentation.registration.RegistrationScreen
import hu.levente.fazekas.androidApp.presentation.reservationsList.ReservationScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.DeviceListScreen.route
    ){
        composable(route = Screen.DeviceListScreen.route){
            DeviceListScreen(
                onNavigate = navController::navigate,
                navController = navController,
                target = Screen.LoginScreen.route+"/login"
            )
        }
        composable(
            route = Screen.DeviceDetailScreen.route+"/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.IntType
                    nullable = false
                }
            )
        ){
            it.arguments?.getInt("id")?.let { it1 ->
                DeviceDetail(it1, navController = navController)
            }
        }
        composable(route = Screen.CameraScreen.route+"/lease"){
            LeaseScreen(mode ="lease", navController = navController)
        }
        composable(route = Screen.CameraScreen.route+"/drawback"){
            LeaseScreen(mode = "drawback", navController = navController)
        }
        composable(route = Screen.LoginScreen.route+"/login"){
            LoginScreen(navController = navController, target = Screen.DeviceListScreen.route, mode = "login")
        }
        composable(route = Screen.LoginScreen.route+"/logout"){
            LoginScreen(navController = navController, target = Screen.DeviceListScreen.route, mode = "logout")
        }
        composable(route = Screen.ReservationListScreen.route){
            ReservationScreen(navController = navController, target = Screen.LoginScreen.route+"/login")
        }
        composable(route = Screen.RegistrationScreen.route){
            RegistrationScreen(navController = navController)
        }
        composable(route = Screen.CreateDeviceScreen.route){
            CreateDevice(navController = navController)
        }
    }
}
