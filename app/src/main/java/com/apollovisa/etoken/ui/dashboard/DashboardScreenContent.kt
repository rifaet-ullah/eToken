package com.apollovisa.etoken.ui.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apollovisa.etoken.R
import com.apollovisa.etoken.domain.models.SimCard
import com.apollovisa.etoken.ui.theme.ETokenTheme

@Composable
fun DashboardScreenContent(simCards: List<SimCard> = emptyList()) {
    val scrollState = rememberScrollState()
    val roundedCornerSize = 32.dp

    Scaffold(floatingActionButton = {
        Button(onClick = {}) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add task")
                Text("Add task")
            }
        }
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .scrollable(state = scrollState, orientation = Orientation.Horizontal)
        ) {
            Image(
                painter = painterResource(R.drawable.tokenbd),
                contentDescription = "TokenBD",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        shape = RoundedCornerShape(
                            bottomStart = roundedCornerSize, bottomEnd = roundedCornerSize
                        )
                    )
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Registered SIM", fontWeight = FontWeight.Medium, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                if (simCards.isEmpty()) {
                    Text(
                        "No registered SIM Card found.",
                        fontWeight = FontWeight.Light,
                        fontSize = 14.sp
                    )
                } else {
                    simCards.forEach {
                        RegisteredSimCard(it)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add SIM Card",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .size(48.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(50)
                                )
                                .padding(6.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RegisteredSimCard(
    simCard: SimCard, onClick: () -> Unit = {}, onViewDetailsClick: () -> Unit = {}
) {
    val serviceNumberCode = simCard.phoneNumber.replace("+88", "").substring(0..2)
    val backgroundColor = when (serviceNumberCode) {
        "017" -> Color.Blue.copy(alpha = 0.5f)
        "013" -> Color.Blue.copy(alpha = 0.5f)
        "018" -> Color.Red.copy(alpha = 0.5f)
        "019" -> Color.DarkGray
        else -> Color.White
    }
    val textColor = when (backgroundColor) {
        Color.White -> Color.Black
        else -> Color.White
    }

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = backgroundColor)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    simCard.slotName,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = textColor
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    simCard.phoneNumber,
                    fontWeight = FontWeight.Light,
                    fontSize = 16.sp,
                    color = textColor
                )
            }
            IconButton(onClick = onViewDetailsClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowForwardIos,
                    contentDescription = "View Details",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(28.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(50)
                        )
                        .padding(6.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardScreenContentPreview() {
    ETokenTheme {
        DashboardScreenContent()
    }
}
