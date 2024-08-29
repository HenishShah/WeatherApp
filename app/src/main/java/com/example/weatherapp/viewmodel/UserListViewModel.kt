package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor() : ViewModel() {

    private val _userListState = MutableStateFlow(UserListState(null, null, null))
    val userListState: StateFlow<UserListState> = _userListState

    fun onAddUserClicked(email: String, firstName: String, lastName: String, onConfirm: (String, String, String) -> Unit, onDismiss: () -> Unit) {
        val emailError =
            if (email.trim().isEmpty()) "Email cannot be empty"
            else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Invalid email address"
            else null

        val firstNameError =
            if (firstName.trim().isEmpty()) "First name cannot be empty"
            else null

        val lastNameError =
            if (lastName.trim().isEmpty()) "Last name cannot be empty"
            else null

        if (emailError.isNullOrEmpty() && firstNameError.isNullOrEmpty() && lastNameError.isNullOrEmpty()) {
            _userListState.value = _userListState.value.copy(
                emailError = emailError,
                firstNameError = firstNameError,
                lastNameError = lastNameError
            )
            onConfirm(firstName, lastName, email)
            onDismiss()
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

