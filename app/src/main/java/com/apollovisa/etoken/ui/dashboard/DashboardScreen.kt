package com.apollovisa.etoken.ui.dashboard

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DashboardScreen(viewModel: DashboardScreenViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val requiredPermissions = arrayOf(
        Manifest.permission.READ_SMS,
        Manifest.permission.RECEIVE_SMS
    )
    var permissionGranted = requiredPermissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissionGranted = permissions.all {
            Log.d("DashboardScreen", "Permission ${it.key}: ${it.value}")
            it.value == true
        }
    }

    LaunchedEffect(key1 = permissionGranted) {
        if (!permissionGranted) {
            permissionLauncher.launch(requiredPermissions)
        } else {
            viewModel.fetchInitialMessage()
        }
    }

    DashboardScreenContent()
}
