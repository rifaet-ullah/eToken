package com.apollovisa.etoken.ui.sim

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollovisa.etoken.domain.repository.SimCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SimCardViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val simCardRepository: SimCardRepository
) : ViewModel() {
    private val _uiState: MutableState<SimCardUIState> = mutableStateOf(SimCardUIState.Loading)
    val uiState: State<SimCardUIState> = _uiState

    init {
        val slotIndex = savedStateHandle.get<Int>("slotIndex")
        if (slotIndex == null) {
            _uiState.value = SimCardUIState.SimCardDeleted
        } else {
            viewModelScope.launch {
                simCardRepository.getSimCardBySlotIndex(slotIndex)?.let {
                    _uiState.value = SimCardUIState.Initial(
                        simCard = it
                    )
                }
            }
        }
    }

    fun onRemoveSimCard() {
        viewModelScope.launch {
            when (_uiState.value) {
                is SimCardUIState.Initial -> {
                    simCardRepository.delete(
                        slotIndex = (_uiState.value as SimCardUIState.Initial).simCard.slotIndex
                    )
                    _uiState.value = SimCardUIState.SimCardDeleted
                }

                else -> {}
            }
        }
    }
}
