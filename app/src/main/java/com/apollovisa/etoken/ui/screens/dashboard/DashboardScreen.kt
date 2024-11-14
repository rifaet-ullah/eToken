package com.apollovisa.etoken.ui.screens.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DashboardScreen(viewModel: DashboardScreenViewModel = hiltViewModel()) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.getSMSMessages(context, "inbox")
        viewModel.getSMSMessages(context, "sent")
    }

    DashboardScreenContent(messages = viewModel.smsMessages.value)
}
