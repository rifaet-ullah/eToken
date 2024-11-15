package com.apollovisa.etoken.ui.screens.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor() : ViewModel() {
    private val _viewState: MutableState<LogInViewState> = mutableStateOf(
        LogInViewState.InputState()
    )
    val viewState: State<LogInViewState> = _viewState

    fun onEmailChange(email: String) {
        _viewState.value = (_viewState.value as LogInViewState.InputState).copy(
            inputEmail = email
        )
    }

    fun onPasswordChange(password: String) {
        _viewState.value = (_viewState.value as LogInViewState.InputState).copy(
            inputPassword = password
        )
    }

    fun onLogInButtonClick() {
        _viewState.value = LogInViewState.AuthenticationState
        viewModelScope.launch {
            delay(2000)
            _viewState.value = LogInViewState.SuccessState
        }
    }
}
