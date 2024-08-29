package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class UserListViewModel: ViewModel() {

    private val _userListState = MutableStateFlow(UserListState(null, null, null))
    val userListState: StateFlow<UserListState> = _userListState

    fun onAddUserClicked(email: String, firstName: String, lastName: String, onConfirm: (String, String, String) -> Unit) {
        val emailError =
            if (email.trim().isEmpty()) "Email cannot be empty"
            else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Invalid email address"
            else ""

        val firstNameError =
            if (firstName.trim().isEmpty()) "First name cannot be empty"
            else ""

        val lastNameError =
            if (lastName.trim().isEmpty()) "Last name cannot be empty"
            else ""

        if (emailError.isEmpty() && firstNameError.isEmpty() && lastNameError.isEmpty()) {
            onConfirm(firstName, lastName, email)
            _userListState.value = _userListState.value.copy(
                emailError = null,
                firstNameError = null,
                lastNameError = null
            )
        } else {
            _userListState.value = _userListState.value.copy(
                emailError = emailError,
                firstNameError = firstNameError,
                lastNameError = lastNameError
            )
        }
    }

}

data class UserListState(
    val emailError: String?,
    val firstNameError: String?,
    val lastNameError: String?
)

