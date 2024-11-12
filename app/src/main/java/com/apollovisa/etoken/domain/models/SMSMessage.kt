package com.apollovisa.etoken.domain.models

import java.time.LocalDateTime

data class SMSMessage(
    val sender: String,
    val dateTime: LocalDateTime,
    val details: String,
)
