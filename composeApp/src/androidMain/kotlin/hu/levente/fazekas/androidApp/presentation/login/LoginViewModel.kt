package hu.levente.fazekas.androidApp.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import hu.bme.aut.android.eszkozkolcsonzo.presentation.login.LoginEvent
import hu.bme.aut.android.eszkozkolcsonzo.presentation.login.LoginState
import hu.levente.fazekas.shared.model.User
import hu.levente.fazekas.shared.network.NetworkInterface
import hu.levente.fazekas.shared.use_case.ValidateEmail
import hu.levente.fazekas.shared.use_case.ValidatePassword
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel constructor(
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val network: NetworkInterface
) : ViewModel() {
    var state by mutableStateOf(LoginState())

    private val validationEventChannel = Channel<LoginScreenEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {

        }
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                val emailResult = validateEmail.execute(event.email)
                state = state.copy(email = event.email, emailError = emailResult.errorMessage)
            }
            is LoginEvent.PasswordChanged -> {
                val passwordResult = validatePassword.execute(event.password)
                state = state.copy(
                    password = event.password,
                    passwordError = passwordResult.errorMessage
                )
            }
            is LoginEvent.StayLoggedInCheckBoxChanged -> {
                state = state.copy(stayLoggedIn = event.stayLoggedIn)
            }
            is LoginEvent.Login -> {
                state = state.copy(isLoading = true)
                submitData()
            }
            is LoginEvent.CancelLogout -> {
                cancelLogout()
            }
            is LoginEvent.Logout -> {
                logout()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )
            return
        }
        viewModelScope.launch {
            try {
                val token = network.login(state.email, state.password)
                val jwt = JWT(token)
                val id = jwt.getClaim("id").asInt()
                val name = jwt.getClaim("name").asString()
                val email = jwt.getClaim("email").asString()
                val privilege = when(jwt.getClaim("priv").asString()){
                    "Admin" -> User.Privilege.Admin
                    "User" -> User.Privilege.User
                    "Handler" -> User.Privilege.Handler
                    else -> User.Privilege.User
                }
                validationEventChannel.send(LoginScreenEvent.LoginSuccess)
            } catch (e: Exception) {
                validationEventChannel.send(LoginScreenEvent.LoginFailed)
            }
        }
    }

    private fun cancelLogout() {
        viewModelScope.launch {
            validationEventChannel.send(LoginScreenEvent.LogoutFailed)
        }
    }

    private fun logout() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            validationEventChannel.send(LoginScreenEvent.LogoutSuccess)
            state = state.copy(isLoading = false)
        }
    }

    sealed class LoginScreenEvent {
        object LoginSuccess : LoginScreenEvent()
        object LoginFailed : LoginScreenEvent()
        object LogoutSuccess : LoginScreenEvent()
        object LogoutFailed : LoginScreenEvent()
    }
}