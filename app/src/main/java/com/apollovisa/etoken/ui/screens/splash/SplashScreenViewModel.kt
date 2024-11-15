package com.apollovisa.etoken.ui.screens.splash

import androidx.lifecycle.ViewModel
import com.apollovisa.etoken.domain.models.User
import com.apollovisa.etoken.domain.service.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val userService: UserService
) : ViewModel() {

    fun getUser(): User? {
        return userService.getAuthenticatedUser()
    }
}
