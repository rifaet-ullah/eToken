package com.apollovisa.etoken.ui.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apollovisa.etoken.R
import com.apollovisa.etoken.ui.theme.ETokenTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreenContent() {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit) {
        delay(100)
        visible = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        AnimatedVisibility(visible = visible) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(200.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.timelytravel),
                    contentDescription = "Message Icon",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    "TIMELY",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    "TRAVEL",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenContentPreview() {
    ETokenTheme {
        SplashScreenContent()
    }
}
