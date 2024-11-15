package com.apollovisa.etoken.ui.screens.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onAuthenticationSuccess: () -> Unit,
    onAuthenticationFailure: () -> Unit,
    viewModel: SplashScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        delay(1000)
        val user = viewModel.getUser()
        if (user == null) {
            onAuthenticationFailure()
        } else {
            onAuthenticationSuccess()
        }
    }

    SplashScreenContent()
}
