package com.example.weatherapp.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.common.DataStoreManager
import com.example.weatherapp.di.DataStoreEntryPoint
import com.example.weatherapp.model.UserModel
import com.example.weatherapp.viewmodel.LoginEffect
import com.example.weatherapp.viewmodel.LoginViewModel
import com.example.weatherapp.viewmodel.UserViewModel
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MainScreen(modifier: Modifier = Modifier, userViewModel: UserViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        loginViewModel.loginEffect.collectLatest {
            when (it) {
                LoginEffect.LoginSuccess -> navController.navigate("userList") {
                    popUpTo(0) { inclusive = true }
                }
                LoginEffect.LogoutSuccess -> navController.navigate("login") {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen()
        }
        composable("login") {
            LoginScreen(loginViewModel = loginViewModel)
        }

        composable("userList") {
            UserListingScreen(
                viewModel = userViewModel,
                onAddUserClick = { navController.navigate("addUser") },
                onUserClick = { navController.navigate("details") }
            )
        }
        composable("details") {
            WeatherDetailScreen(
                onBackClick = { navController.popBackStack() },
                loginViewModel = loginViewModel
            )
        }

        dialog("addUser") {
            UserInputDialog(
                onDismissRequest = { navController.popBackStack() },
                onConfirm = { firstName, lastName, email ->
                    userViewModel.insertUser(UserModel(email, firstName, lastName))
                }
            )
        }
    }
}