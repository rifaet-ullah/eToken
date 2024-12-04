package com.apollovisa.etoken.ui.dashboard

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollovisa.etoken.domain.models.SimCard
import com.apollovisa.etoken.domain.repository.SimCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardScreenViewModel @Inject constructor(
    private val simCardRepository: SimCardRepository
) : ViewModel() {
    private val _uiState: MutableState<DashboardUIState> = mutableStateOf(
        DashboardUIState.Initial(emptyList())
    )
    val uiState: State<DashboardUIState> = _uiState

    suspend fun initUiState() {
        _uiState.value = DashboardUIState.Initial(cards = simCardRepository.getSimCards())
    }

    fun onAddSimCardButtonClick() {
        _uiState.value = DashboardUIState.AddSimCardPopUp(
            existingSimCards = uiState.value.simCards,
            newSimCard = null
        )
    }

    fun onDismissRegisterNewSim() {
        viewModelScope.launch {
            _uiState.value = DashboardUIState.Initial(cards = simCardRepository.getSimCards())
        }
    }

    fun onRegisterNewSim(simSlot: String, simNumber: String) {
        viewModelScope.launch {
            simCardRepository.add(
                simCard = SimCard(
                    slotIndex = simSlot.last().digitToInt() - 1,
                    slotName = simSlot,
                    phoneNumber = simNumber
                )
            )
            _uiState.value = DashboardUIState.Initial(cards = simCardRepository.getSimCards())
        }
    }

    fun onRemoveSimCard(simCard: SimCard) {
        viewModelScope.launch {
            simCardRepository.delete(simCard.slotIndex)
            _uiState.value = DashboardUIState.Initial(cards = simCardRepository.getSimCards())
        }
    }
}
