package com.apollovisa.etoken.ui.screens.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(actionAfterDelay: () -> Unit) {
    LaunchedEffect(key1 = true) {
        delay(1000)
        actionAfterDelay()
    }

    SplashScreenContent()
}
