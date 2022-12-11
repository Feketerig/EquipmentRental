package hu.levente.fazekas.shared.network

import hu.levente.fazekas.shared.model.Device
import hu.levente.fazekas.shared.model.Reservation
import hu.levente.fazekas.shared.model.User
import hu.levente.fazekas.shared.utils.hash.sha256
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import org.koin.core.component.KoinComponent

class Network(
    private val client: HttpClient
): NetworkInterface, KoinComponent {
    override suspend fun login(email: String, password: String): String {
        return try {
            client.post("${NetworkInterface.Endpoints.User.url}/login"){
                setBody("$email|${password.sha256()}")
            }.body<String>()
        } catch (e: Exception){
            e.printStackTrace()
            ""
        }
    }

    override suspend fun registration(email: String, name: String, phone: String, address: String, password: String) {
        try {
            client.submitForm(NetworkInterface.Endpoints.User.url){
                parameter("name", name)
                parameter("email", email)
                parameter("phone", phone)
                parameter("address", address)
                parameter("pwHash", password.sha256())
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    override suspend fun getAllDevices(): List<Device> {
        return try {
            client.get(NetworkInterface.Endpoints.Device.url).body()
        }catch (e: Exception){
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getDevice(id: Int): Device? {
        return try {
            client.get("${NetworkInterface.Endpoints.Device.url}/" + id.toString()).body()
        }catch (e: Exception){
            null
        }
    }

    override suspend fun addDevice(device: Device): Int {
        try {
            client.submitForm(NetworkInterface.Endpoints.Device.url){
                parameter("name",device.name)
                parameter("desc",device.desc)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
        return 1
    }

    /*override suspend fun deleteDevice(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getActiveLeases(): List<Lease> {
        TODO("Not yet implemented")
    }

    override suspend fun getLease(id: Int): Lease {
        TODO("Not yet implemented")
    }*/

    override suspend fun getLeaseIdByReservationId(id: Int): Int {
        return try {
            client.get("${NetworkInterface.Endpoints.Lease.url}/reservation/$id").body()
        } catch (e: Exception){
            e.printStackTrace()
            0
        }
    }

    override suspend fun addLease(reservationId: Int, handlerUserId: Int, requesterUserId: Int) {
        try {
            client.submitForm(NetworkInterface.Endpoints.Lease.url){
                parameter("resId", reservationId)
                parameter("kiado", handlerUserId)
                parameter("atvevo", requesterUserId)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    /*override suspend fun deleteLease(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun activateLease(id: Int) {
        TODO("Not yet implemented")
    }*/

    override suspend fun deactivateLease(id: Int) {
        try {
            client.put("${NetworkInterface.Endpoints.Lease.url}/$id")
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    override suspend fun getAllReservations(): List<Reservation> {
        return try {
            client.get(NetworkInterface.Endpoints.Reservation.url).body()
        }catch (e: Exception){
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getAllReservationByUserId(id: Int): List<Reservation> {
        return try {
            client.get("${NetworkInterface.Endpoints.Reservation.url}/user").body()
        }catch (e: Exception){
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getReservationByDeviceId(id: Int): Reservation? {
        return try {
            client.get("${NetworkInterface.Endpoints.Reservation.url}/device/$id").body()
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    /*override suspend fun getReservation(id: Int): Reservation {
        TODO("Not yet implemented")
    }*/

    override suspend fun addReservation(deviceId: Int, startDate: Long, endDate: Long) {
        try {
            client.submitForm{
                url(NetworkInterface.Endpoints.Reservation.url)
                parameter("deviceid", deviceId)
                parameter("from", startDate)
                parameter("to", endDate)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }

    }

    /*override suspend fun deleteReservation(id: Int) {
        TODO("Not yet implemented")
    }*/

    override suspend fun getUserNameById(userId: Int): String {
        return try {
            client.get("${NetworkInterface.Endpoints.User.url}/$userId/name").body()
        } catch (e: Exception){
            e.printStackTrace()
            ""
        }
    }

    override suspend fun getUserById(userId: Int): User? {
        return try {
            client.get("${NetworkInterface.Endpoints.User.url}/$userId").body()
        } catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    override suspend fun getUserByEmail(email: String): User? {
        return try {
            client.get(NetworkInterface.Endpoints.User.url).body()
        } catch (e: Exception){
            e.printStackTrace()
            throw e
        }
    }

    companion object{
        val bearerTokenStorage = mutableListOf<BearerTokens>()
        val email: String = ""
        val password: String = ""
    }
}