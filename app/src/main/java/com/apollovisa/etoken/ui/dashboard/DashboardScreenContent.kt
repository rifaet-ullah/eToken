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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.apollovisa.etoken.R
import com.apollovisa.etoken.domain.models.SimCard
import com.apollovisa.etoken.ui.theme.ETokenTheme

@Composable
fun DashboardScreenContent(
    uiState: DashboardUIState,
    onAddSimCardButtonClick: () -> Unit = {},
    onDismissRegisterNewSimCardPopUp: () -> Unit = {},
    onRegisterNewSimCardClick: (String, String) -> Unit = { _, _ -> },
    onRemoveSimCard: (SimCard) -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val roundedCornerSize = 32.dp
    val registeredSimSlots = uiState.simCards.map { it.slotName }

    Scaffold { paddingValues ->
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
                if (uiState.simCards.isEmpty()) {
                    Text(
                        "No registered SIM Card found.",
                        fontWeight = FontWeight.Light,
                        fontSize = 14.sp
                    )
                } else {
                    uiState.simCards.forEach {
                        RegisteredSimCard(simCard = it, onRemoveClick = onRemoveSimCard)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                if (uiState.simCards.size < 3) {
                    if (uiState is DashboardUIState.AddSimCardPopUp) {
                        AddSimCardPopUp(
                            availableSimSlots = uiState.validSimSlots.filter {
                                !registeredSimSlots.contains(it)
                            },
                            onDismissRequest = onDismissRegisterNewSimCardPopUp,
                            onAddSimCardClick = onRegisterNewSimCardClick
                        )
                    }

                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                        IconButton(onClick = onAddSimCardButtonClick) {
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
}

@Composable
private fun RegisteredSimCard(
    simCard: SimCard, onClick: () -> Unit = {}, onRemoveClick: (SimCard) -> Unit = {}
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
            IconButton(
                onClick = { onRemoveClick(simCard) }
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Remove SIM Card",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(28.dp)
                        .background(
                            color = MaterialTheme.colorScheme.error,
                            shape = RoundedCornerShape(50)
                        )
                        .padding(6.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddSimCardPopUp(
    availableSimSlots: List<String>,
    onDismissRequest: () -> Unit,
    onAddSimCardClick: (String, String) -> Unit
) {
    var showDropDownMenu by remember { mutableStateOf(false) }
    var simNumber by remember { mutableStateOf("") }
    var selectedSimSlot by remember { mutableStateOf(availableSimSlots[0]) }

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Register New SIM", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = simNumber,
                    onValueChange = {
                        simNumber = it
                    },
                    placeholder = { Text("SIM Number") },
                    label = { Text("SIM Number") },
                    trailingIcon = {
                        TextButton(
                            onClick = {
                                showDropDownMenu = !showDropDownMenu
                            }
                        ) {
                            Text(selectedSimSlot)
                        }
                    }
                )
                if (availableSimSlots.size > 1) {
                    DropdownMenu(
                        expanded = showDropDownMenu,
                        onDismissRequest = { showDropDownMenu = false },
                        offset = DpOffset(x = 0.dp, y = (-40).dp)
                    ) {
                        availableSimSlots.forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    selectedSimSlot = it
                                    showDropDownMenu = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    TextButton(
                        onClick = {
                            onAddSimCardClick(selectedSimSlot, simNumber)
                        }
                    ) {
                        Text("Add")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardScreenContentPreview() {
    ETokenTheme {
        DashboardScreenContent(uiState = DashboardUIState.AddSimCardPopUp(emptyList(), null))
    }
}
