package com.apollovisa.etoken.ui.broadcast_listener

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import com.apollovisa.etoken.data.dto.OtpReceiveDto
import com.apollovisa.etoken.data.repository.SharedPreferenceSimCardRepository
import com.apollovisa.etoken.domain.models.SMSMessage
import com.apollovisa.etoken.domain.repository.SimCardRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
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

    private val otpIndex = mapOf(
        "IVAC_BD" to 0,
        "01708404440" to 0,
        "bKash" to 5,
        "NAGAD" to 9,
        "IVAC FEES" to 6
    )

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.provider.Telephony.SMS_RECEIVED") {
            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)

            val slotIndex = intent.extras?.getInt("android.telephony.extra.SLOT_INDEX") ?: return
            val sender = messages[0].originatingAddress ?: return
            val fullMessage = messages
                .map { it.displayMessageBody }
                .reduce { aggregate, partial ->
                    "$aggregate $partial"
                }

            val sharedPreferences = context?.getSharedPreferences(
                "SIM_CARD", Context.MODE_PRIVATE
            ) ?: return
            val simCardRepository: SimCardRepository = SharedPreferenceSimCardRepository(
                sharedPreferences
            )

            CoroutineScope(Dispatchers.IO).launch {
                val simCard = simCardRepository.getSimCards().find { it.slotIndex == slotIndex }
                val receiver = simCard?.phoneNumber ?: return@launch

                sendOTP(
                    smsMessage = SMSMessage(
                        sender = sender,
                        receiver = receiver,
                        message = fullMessage,
                        timestamp = messages[0].timestampMillis
                    )
                )
            }
        }
    }

    suspend fun sendOTP(smsMessage: SMSMessage) {
        Log.d("sendOTP", "SMS: $smsMessage")
        val index = otpIndex[smsMessage.sender] ?: return

        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json()
            }
        }

        val date = Date(smsMessage.timestamp)
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        var currentDatetime = System.currentTimeMillis()
        val endDatetime = currentDatetime + 10 * 60 * 1000

        while (currentDatetime < endDatetime) {
            try {
                Log.d("sendOTP", "Trying with: $smsMessage")
                val response = client.post("http://3.131.9.114/IndianVisaOtp/newOtpReceive.php") {
                    headers {
                        append(HttpHeaders.ContentType, "application/json")
                    }

                    setBody(
                        mapOf(
                            "sender" to smsMessage.sender,
                            "receiver" to smsMessage.receiver,
                            "message" to smsMessage.message,
                            "receivedAt" to formatter.format(date),
                            "otp" to smsMessage.message.split(" ")[index]
                        )
                    )
                }
                if (response.status == HttpStatusCode.OK) {
                    val data = response.body<OtpReceiveDto>()
                    Log.d("sendOTP", "Response: $data")
                    break
                } else currentDatetime = System.currentTimeMillis()
            } catch (e: Exception) {
                Log.e("IncomingSMSListener", "Exception message: ${e.message}")
                delay(1000)
            }
        }
    }
}
