package com.apollovisa.etoken.domain.service

import com.apollovisa.etoken.domain.models.User

interface UserService {
    fun getAuthenticatedUser(): User?
}
