package com.apollovisa.etoken.ui.dashboard

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.apollovisa.etoken.domain.models.SMSMessage
import com.apollovisa.etoken.domain.service.SMSMessageService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardScreenViewModel @Inject constructor(
    private val smsMessageService: SMSMessageService
) : ViewModel() {
    val smsMessages: MutableState<List<SMSMessage>> = mutableStateOf(emptyList())

    suspend fun fetchInitialMessage() {
        smsMessages.value = smsMessageService
            .getLastMessageBySenders()
            .sortedBy { it.timestamp }
            .reversed()
    }
}
