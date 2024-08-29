package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.DataState
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

    private val _dataState: MutableStateFlow<DataState<WeatherModel?>> =
        MutableStateFlow(DataState.Loading)
    val dataState: StateFlow<DataState<WeatherModel?>> = _dataState

    fun fetchWeatherDetails() {
        viewModelScope.launch {
            _dataState.emit(DataState.Loading)
            val result = repository.getWeatherDetails()

            if (result.isSuccess) {
                _dataState.emit(
                    DataState.Success(
                        result.getOrNull()
                    )
                )
            } else {
                _dataState.emit(
                    DataState.Error(
                        result.exceptionOrNull()?.message ?: "Unknown Error"
                    )
                )
            }
        }
    }
}