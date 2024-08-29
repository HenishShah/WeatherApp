package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginState(false, null, null, null))
    val loginState: StateFlow<LoginState> = _loginState

    private val _loginEffect: MutableSharedFlow<LoginEffect> = MutableSharedFlow(replay = 0)
    val loginEffect: SharedFlow<LoginEffect> = _loginEffect

    init {
        viewModelScope.launch {
            dataStoreManager.isLoggedIn.collect { loggedIn ->
                if (loggedIn) {
                    _loginEffect.emit(LoginEffect.LoginSuccess)
                } else {
                    _loginEffect.emit(LoginEffect.LogoutSuccess)
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStoreManager.setLoggedInState(false)
        }
    }

    fun togglePasswordVisibility() {
        _loginState.value =
            _loginState.value.copy(isPasswordVisible = !_loginState.value.isPasswordVisible)
    }

    fun onLoginClicked(email: String, password: String) {
        val emailError =
            if (email.isEmpty()) "Email cannot be empty"
            else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                    .matches()
            ) "Invalid email address"
            else null

        val passwordError =
            if (password.isEmpty()) "Password cannot be empty"
            else null

        if (emailError.isNullOrEmpty() && passwordError.isNullOrEmpty()) {
            val commonError = if (email == "testapp@google.com" && password == "Test@123456") {
                login()
                null
            } else {
                "Invalid credentials"
            }

            _loginState.value = _loginState.value.copy(
                emailError = emailError,
                passwordError = passwordError,
                commonError = commonError
            )
        } else {
            _loginState.value =
                _loginState.value.copy(emailError = emailError, passwordError = passwordError)
        }
    }

    private fun login() {
        viewModelScope.launch {
            dataStoreManager.setLoggedInState(true)
        }
    }
}

data class LoginState(
    val isPasswordVisible: Boolean,
    val emailError: String?,
    val passwordError: String?,
    val commonError: String?
)

sealed class LoginEffect {
    object LoginSuccess : LoginEffect()
    object LogoutSuccess : LoginEffect()

}