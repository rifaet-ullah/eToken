package com.apollovisa.etoken.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log

class IncomingSMSListener: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.provider.Telephony.SMS_RECEIVED") {
            val bundle = intent.extras
            if (bundle != null) {
                val pdus = bundle.get("pdus") as Array<*>
                pdus.forEach { pdu ->
                    val smsMessage = SmsMessage.createFromPdu(pdu as ByteArray)
                    val messageBody = smsMessage.messageBody
                    val sender = smsMessage.originatingAddress

                    // Here, we could trigger a callback or an event
                    Log.d("SmsReceiver", "Received SMS from: $sender, Message: $messageBody")
                }
            }
        }
    }
}
