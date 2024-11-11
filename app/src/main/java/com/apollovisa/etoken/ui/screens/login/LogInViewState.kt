package com.apollovisa.etoken.ui.screens.login

sealed class LogInViewState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val errorMessage: String? = null
) {
    data class InputState(
        val inputEmail: String = "",
        val inputEmailError: String? = null,
        val inputPassword: String = "",
        val inputPasswordError: String? = null
    ) : LogInViewState(
        email = inputEmail,
        emailError = inputEmailError,
        password = inputPassword,
        passwordError = inputPasswordError
    )

    object AuthenticationState : LogInViewState()

    object SuccessState : LogInViewState()
}
