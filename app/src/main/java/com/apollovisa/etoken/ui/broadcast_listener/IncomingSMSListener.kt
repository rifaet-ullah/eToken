package com.apollovisa.etoken.ui.broadcast_listener

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import com.apollovisa.etoken.domain.models.SMSMessage
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class IncomingSMSListener : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.provider.Telephony.SMS_RECEIVED") {
            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)

            val sender = messages[0].originatingAddress ?: return
            val fullMessage = messages
                .map { it.displayMessageBody }
                .reduce { aggregate, partial ->
                    "$aggregate $partial"
                }
            CoroutineScope(Dispatchers.IO).launch {
                sendOTP(
                    smsMessage = SMSMessage(
                        sender = sender,
                        receiver = "",
                        message = fullMessage,
                        timestamp = messages[0].timestampMillis
                    )
                )
            }
        }
    }

    suspend fun sendOTP(smsMessage: SMSMessage) {
        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json()
            }
        }

        val date = Date(smsMessage.timestamp)
        val formatter = SimpleDateFormat("HH:mm dd-MM-yyyy", Locale.getDefault())
        var currentDatetime = System.currentTimeMillis()
        val endDatetime = currentDatetime + 10 * 60 * 1000

        while (currentDatetime < endDatetime) {
            try {
                Log.d("sendOTP", "Trying with: $smsMessage")
                val response = client.post("https://example.com/api/v1/end-point") {
                    headers {
                        append(HttpHeaders.ContentType, "application/json")
                    }

                    setBody(
                        mapOf(
                            "sender" to smsMessage.sender,
                            "receiver" to smsMessage.receiver,
                            "message" to smsMessage.message,
                            "receivedAt" to formatter.format(date),
                            "otp" to smsMessage.message.split(" ").first()
                        )
                    )
                }
                if (response.status == HttpStatusCode.OK) break
                else currentDatetime = System.currentTimeMillis()
            } catch (e: Exception) {
                Log.e("IncomingSMSListener", "Exception message: ${e.message}")
                delay(1000)
            }
        }
    }
}
