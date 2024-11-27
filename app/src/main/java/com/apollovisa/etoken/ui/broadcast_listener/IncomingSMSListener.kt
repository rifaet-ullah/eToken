package com.apollovisa.etoken.ui.broadcast_listener

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class IncomingSMSListener : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.provider.Telephony.SMS_RECEIVED") {
            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)

            messages.forEach {
                val date = Date(it.timestampMillis)
                val formatter = SimpleDateFormat("HH:mm dd-MM-yyyy", Locale.getDefault())

                Log.d("IncomingSMSListener", "Sender: ${it.originatingAddress}")
                Log.d("IncomingSMSListener", "Time: ${formatter.format(date)}")
                Log.d("IncomingSMSListener", "Message body: ${it.displayMessageBody}")
                Log.d("IncomingSMSListener", "OTP code: ${it.displayMessageBody.split(" ").first()}")
            }
        }
    }
}
