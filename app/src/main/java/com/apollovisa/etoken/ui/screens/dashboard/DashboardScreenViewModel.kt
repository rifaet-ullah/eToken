package com.apollovisa.etoken.ui.screens.dashboard

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.apollovisa.etoken.domain.models.SMSMessage
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class DashboardScreenViewModel : ViewModel() {
    val smsMessages: MutableState<List<SMSMessage>> = mutableStateOf(emptyList())

    fun getSMSMessages(context: Context, type: String) {
        val messages = mutableListOf<SMSMessage>()
        val cursor =
            context.contentResolver.query(Uri.parse("content://sms/$type"), null, null, null, null)

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
            smsMessages.value += messages
        }
    }
}
