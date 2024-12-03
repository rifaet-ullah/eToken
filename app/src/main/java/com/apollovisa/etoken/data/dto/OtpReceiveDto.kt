package com.apollovisa.etoken.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class OtpReceiveDto(
    val status: String,
    val otp: String
)
