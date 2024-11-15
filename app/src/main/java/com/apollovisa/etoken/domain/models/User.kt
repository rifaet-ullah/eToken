package com.apollovisa.etoken.domain.models

data class User(
    val name: String,
    val email: String,
    val lastLoginTime: Long
)
