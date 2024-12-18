package com.apollovisa.etoken.ui.sim

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SimCardScreen(
    onSimCardDeletionComplete: () -> Unit = {},
    viewModel: SimCardViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = viewModel.uiState.value) {
        if (viewModel.uiState.value is SimCardUIState.SimCardDeleted) {
            onSimCardDeletionComplete()
        }
    }

    SimCardScreenContent(
        uiState = viewModel.uiState.value,
        onBackButtonClick = onSimCardDeletionComplete,
        onDeleteButtonClick = viewModel::onRemoveSimCard
    )
}
