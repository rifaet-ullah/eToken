package com.apollovisa.etoken.data.service

import android.content.Context
import android.net.Uri
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
        val cursor = resolver.query(Uri.parse("content://sms/$type"), null, null, null, null)
        cursor?.use {
            val indexMessage = it.getColumnIndex("body")
            val indexSender = it.getColumnIndex("address")
            val indexDate = it.getColumnIndex("date")
            val indexRead = it.getColumnIndex("read")
            val indexType = it.getColumnIndex("type")
            val indexThread = it.getColumnIndex("thread_id")
            val indexService = it.getColumnIndex("service_center")

            while (it.moveToNext()) {
                messages.add(
                    SMSMessage(
                        message = it.getString(indexMessage),
                        sender = it.getString(indexSender),
                        date = it.getLong(indexDate),
                        read = it.getString(indexRead).toBoolean(),
                        type = it.getInt(indexType),
                        thread = it.getInt(indexThread),
                        service = it.getString(indexService) ?: ""
                    )
                )
            }
        }

        return messages
    }
}
