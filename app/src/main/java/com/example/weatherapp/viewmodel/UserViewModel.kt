package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.UserRepository
import com.example.weatherapp.model.UserModel
import com.example.weatherapp.repository.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _usersStateFlow = MutableStateFlow<List<UserModel>>(emptyList())
    val usersStateFlow: StateFlow<List<UserModel>> = _usersStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllUser().collect { items ->
                _usersStateFlow.value = items
            }
        }
    }

    fun insertUser(userModel: UserModel) {
        viewModelScope.launch {
            repository.insertUser(userModel)
        }
    }

    fun deleteUser(userModel: UserModel) {
        viewModelScope.launch {
            repository.deleteUser(
                userModel
            )
        }
    }


}