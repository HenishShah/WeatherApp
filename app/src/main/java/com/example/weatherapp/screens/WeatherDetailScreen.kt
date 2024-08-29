package com.example.weatherapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapp.model.WeatherModel
import com.example.weatherapp.viewmodel.LoginViewModel
import com.example.weatherapp.viewmodel.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDetailScreen(
    weatherViewModel: WeatherViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel,
    onBackClick: () -> Unit
) {

    val showLoader by weatherViewModel.showLoader.collectAsState()
    val weatherModel by weatherViewModel.weatherDetails.collectAsState()
    val error by weatherViewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        weatherViewModel.fetchWeatherDetails()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Box(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                    }

                    Text("Weather Details", modifier = Modifier.align(Alignment.Center))

                    Text(
                        "Logout",
                        fontSize = 10.sp,
                        color = MaterialTheme.colors.error,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .clickable {
                                loginViewModel.logout()
                            }
                    )
                }
            })
        },
        content = { paddingValues ->
            if (showLoader) {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(paddingValues)
                            .size(25.dp)
                            .align(Alignment.Center),
                        color = MaterialTheme.colors.primary
                    )
                }

            } else {
                WeatherDetails(weatherModel, error, paddingValues)
            }
        }
    )
}

@Composable
fun WeatherDetails(
    weatherModel: WeatherModel? = null,
    error: String? = null,
    paddingValues: PaddingValues
) {
    if (error != null && error.isNotEmpty()) {
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        )
        Text(
            text = error,
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center
        )
    } else {
        if (weatherModel != null) {
            Card(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Temperature: ${weatherModel.temp}°C",
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        text = "Weather: ${weatherModel.weatherType}",
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = "Humidity: ${weatherModel.humidity}%",
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = "Wind Speed: ${weatherModel.windSpeed} m/s",
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
            )
            Text(
                text = "No Data Found",
                color = androidx.compose.material3.MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        }
    }

}