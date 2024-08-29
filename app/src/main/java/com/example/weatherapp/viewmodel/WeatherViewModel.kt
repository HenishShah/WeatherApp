package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.WeatherRepository
import com.example.weatherapp.model.WeatherModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _weatherDetails = MutableStateFlow<WeatherModel?>(null)
    val weatherDetails: StateFlow<WeatherModel?> = _weatherDetails

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _showLoader = MutableStateFlow(false)
    val showLoader: StateFlow<Boolean> = _showLoader

    fun fetchWeatherDetails() {
        viewModelScope.launch {
            _showLoader.value = true
            val result = repository.getWeatherDetails()
            _showLoader.value = false
            if (result.isSuccess) {
                _weatherDetails.value = result.getOrNull()
            } else {
                _error.value = result.exceptionOrNull()?.message
            }
        }
    }


}