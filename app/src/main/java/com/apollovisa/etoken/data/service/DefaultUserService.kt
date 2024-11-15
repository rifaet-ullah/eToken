package com.apollovisa.etoken.data.service

import android.content.SharedPreferences
import com.apollovisa.etoken.domain.models.User
import com.apollovisa.etoken.domain.service.UserService

class DefaultUserService(private val sharedPreferences: SharedPreferences) : UserService {
    companion object {
        const val USER_NAME = "USER_NAME"
        const val USER_EMAIL = "USER_EMAIL"
        const val USER_LAST_LOGIN = "USER_LAST_LOGIN"
    }

    override fun getAuthenticatedUser(): User? {
        val name = sharedPreferences.getString(USER_NAME, null)
        val email = sharedPreferences.getString(USER_EMAIL, null)
        val lastLoginTime = sharedPreferences.getLong(USER_LAST_LOGIN, 0)

        if (name == null || email == null || lastLoginTime <= 0) return null

        return User(
            name, email, lastLoginTime
        )
    }
}
