package com.apollovisa.etoken.ui.screens.login

data class LogInScreenData(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val errorMessage: String? = null
)