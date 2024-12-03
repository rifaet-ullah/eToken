package com.apollovisa.etoken.ui.dashboard

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.apollovisa.etoken.domain.models.SimCard
import com.apollovisa.etoken.domain.repository.SimCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardScreenViewModel @Inject constructor(
    private val simCardRepository: SimCardRepository
) : ViewModel() {
    private val _simCards: MutableState<List<SimCard>> = mutableStateOf(emptyList())
    val simCards: State<List<SimCard>> = _simCards

    suspend fun fetchSimCards() {
        _simCards.value = simCardRepository.getSimCards()
    }
}
