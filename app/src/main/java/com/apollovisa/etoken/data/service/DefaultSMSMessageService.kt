package com.apollovisa.etoken.data.service

import android.content.Context
import android.net.Uri
import android.provider.Telephony
import android.util.Log
import com.apollovisa.etoken.domain.models.SMSMessage
import com.apollovisa.etoken.domain.service.SMSMessageService
import kotlin.collections.plus

class DefaultSMSMessageService(context: Context) : SMSMessageService {
    private val resolver = context.contentResolver

    override suspend fun getLastMessageBySenders(): List<SMSMessage> {
        val messages = getAll()

        return messages.groupBy { it.sender }.map { it.value[0] }
    }

    override suspend fun getMessagesBySender(sender: String): List<SMSMessage> {
        val messages = getAll()

        return messages.filter { it.sender == sender }
    }

    fun getAll(): List<SMSMessage> {
        val inbox = getMessagesByType(type = "inbox")
        val sent = getMessagesByType(type = "sent")

        return inbox + sent
    }

    private fun getMessagesByType(type: String): List<SMSMessage> {
        val messages = mutableListOf<SMSMessage>()
        val projection = arrayOf(
            Telephony.Sms.SUBSCRIPTION_ID, Telephony.Sms.ADDRESS, Telephony.Sms.BODY
        )
        val cursor = resolver.query(Uri.parse("content://sms/$type"), projection, null, null, null)
        cursor?.use {
            val indexMessage = it.getColumnIndex("body")
            val indexSender = it.getColumnIndex("address")
            val indexDate = it.getColumnIndex("date")

            Log.d("cursor", "Subscription ID: ${it.getInt(it.getColumnIndexOrThrow(Telephony.Sms.SUBSCRIPTION_ID))}")
            Log.d("cursor", "Subscription ID: ${it.getString(it.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))}")
            Log.d("cursor", "Subscription ID: ${it.getInt(it.getColumnIndexOrThrow(Telephony.Sms.BODY))}")

            while (it.moveToNext()) {
                messages.add(
                    SMSMessage(
                        sender = it.getString(indexSender),
                        receiver = "",
                        message = it.getString(indexMessage),
                        timestamp = it.getLong(indexDate)
                    )
                )
            }
        }

        return messages
    }
}
