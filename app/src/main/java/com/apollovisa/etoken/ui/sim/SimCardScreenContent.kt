package com.apollovisa.etoken.ui.sim

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apollovisa.etoken.domain.models.SimCard
import com.apollovisa.etoken.ui.theme.ETokenTheme

@Composable
fun SimCardScreenContent(
    uiState: SimCardUIState,
    onBackButtonClick: () -> Unit = {},
    onDeleteButtonClick: () -> Unit = {}
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when (uiState) {
                is SimCardUIState.Initial -> {
                    SimCardScreenHeader(
                        simCard = uiState.simCard,
                        onBackButtonClick = onBackButtonClick,
                        onDeleteButtonClick = onDeleteButtonClick
                    )
                }

                else -> {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
private fun SimCardScreenHeader(
    simCard: SimCard,
    onBackButtonClick: () -> Unit = {},
    onDeleteButtonClick: () -> Unit = {}
) {
    val serviceNumberCode = simCard.phoneNumber.replace("+88", "").substring(0..2)
    val backgroundColor = when (serviceNumberCode) {
        "017" -> Color.Blue.copy(alpha = 0.5f)
        "013" -> Color.Blue.copy(alpha = 0.5f)
        "018" -> Color.Red.copy(alpha = 0.5f)
        "019" -> Color.DarkGray
        else -> Color.White
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(252.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            IconButton(onClick = onBackButtonClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBackIos,
                    contentDescription = "Go Back",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            IconButton(onClick = onDeleteButtonClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    simCard.phoneNumber,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Light
                )
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            Text(
                simCard.slotName,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview
@Composable
private fun SimCardScreenContentPreview() {
    ETokenTheme {
        SimCardScreenContent(
            uiState = SimCardUIState.Initial(
                simCard = SimCard(
                    slotIndex = 0,
                    slotName = "SIM1",
                    phoneNumber = "01796135326"
                )
            )
        )
    }
}
