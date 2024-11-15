package com.apollovisa.etoken.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apollovisa.etoken.domain.models.SMSMessage
import com.apollovisa.etoken.domain.models.parsedDate
import com.apollovisa.etoken.ui.theme.ETokenTheme

@Composable
fun DashboardScreenContent(messages: List<SMSMessage>) {
    Scaffold(
        floatingActionButton = {
            Button(onClick = {}) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add task")
                    Text("Add task")
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(messages) {
                Box(modifier = Modifier.padding(12.dp)) {
                    SMSMessageTile(it)
                }
            }
        }
    }
}

@Composable
private fun SMSMessageTile(sms: SMSMessage) {
    Row {
        Icon(
            imageVector = Icons.Outlined.AccountCircle,
            contentDescription = "Account",
            modifier = Modifier.size(32.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(sms.sender, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Text(sms.date.parsedDate(), fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(sms.message)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardScreenContentPreview() {
    ETokenTheme {
        DashboardScreenContent(
            messages = listOf(
                SMSMessage(
                    message = "Big Sale!",
                    sender = "GP",
                    date = 12121212,
                    read = false,
                    type = 1,
                    thread = 1,
                    service = "",
                ),
                SMSMessage(
                    message = "Big Sale!",
                    sender = "Robi",
                    date = 12121212,
                    read = false,
                    type = 1,
                    thread = 1,
                    service = "",
                )
            )
        )
    }
}
