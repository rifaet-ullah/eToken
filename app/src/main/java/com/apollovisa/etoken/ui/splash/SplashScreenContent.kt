package com.apollovisa.etoken.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apollovisa.etoken.R
import com.apollovisa.etoken.ui.theme.ETokenTheme

@Composable
fun SplashScreenContent() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        Image(
            painter = painterResource(id = R.drawable.message_96),
            contentDescription = "Message Icon",
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .size(96.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenContentPreview() {
    ETokenTheme {
        SplashScreenContent()
    }
}
