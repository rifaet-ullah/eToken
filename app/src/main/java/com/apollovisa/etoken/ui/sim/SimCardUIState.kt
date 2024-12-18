package com.apollovisa.etoken.ui.sim

import com.apollovisa.etoken.domain.models.SimCard

sealed class SimCardUIState {
    object Loading : SimCardUIState()

    data class Initial(val simCard: SimCard): SimCardUIState()

    object SimCardDeleted : SimCardUIState()
}
