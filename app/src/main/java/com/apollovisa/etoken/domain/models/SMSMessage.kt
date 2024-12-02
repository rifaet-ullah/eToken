package com.apollovisa.etoken.domain.models

import android.util.Log
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.util.Date
import java.util.Locale

data class SMSMessage(
    val sender: String,
    val receiver: String,
    val message: String,
    val timestamp: Long
)

fun Long.parsedDate(): String {
    val date = Date(this)
    val currentDate = LocalDate.now()
    val timeDifference = Period.between(
        Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate(),
        currentDate
    )
    Log.d("Dateparsing", "Date: $date Difference: ${timeDifference.days} ${timeDifference.months} ${timeDifference.years}")

    val formatter = if (timeDifference.days <= 0) {
        SimpleDateFormat("HH:mm", Locale.getDefault())
    } else if (timeDifference.days <= 7) {
        SimpleDateFormat("EEE", Locale.getDefault())
    } else {
        SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    }

    return formatter.format(date)
}
