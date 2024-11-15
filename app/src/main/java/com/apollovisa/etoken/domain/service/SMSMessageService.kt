package com.apollovisa.etoken.domain.service

import com.apollovisa.etoken.domain.models.SMSMessage

interface SMSMessageService {
    suspend fun getLastMessageBySenders(): List<SMSMessage>

    suspend fun getMessagesBySender(sender: String): List<SMSMessage>
}
