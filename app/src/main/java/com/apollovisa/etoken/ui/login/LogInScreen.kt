package com.apollovisa.etoken.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LogInScreen(onLogInSuccessful: () -> Unit, viewModel: LogInViewModel = hiltViewModel()) {
    LaunchedEffect(key1 = viewModel.viewState.value) {
        if (viewModel.viewState.value is LogInViewState.SuccessState) {
            onLogInSuccessful()
        }
    }

    LogInScreenContent(
        viewState = viewModel.viewState.value,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLogInClick = viewModel::onLogInButtonClick
    )
}
