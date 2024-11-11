package com.apollovisa.etoken.ui.screens.login

import androidx.compose.runtime.Composable

@Composable
fun LogInScreen(onLogInSuccessful: () -> Unit) {
    LogInScreenContent(
        viewState = LogInViewState.InputState(),
        onEmailChange = {},
        onPasswordChange = {},
        onLogInClick = {}
    )
}