package com.apollovisa.etoken.ui.dashboard

import com.apollovisa.etoken.domain.models.SimCard

sealed class DashboardUIState(
    val simCards: List<SimCard>,
    val validSimSlots: List<String> = listOf("SIM1", "SIM2", "SIM3")
) {
    data class Initial(val cards: List<SimCard>) : DashboardUIState(cards)

    data class AddSimCardPopUp(
        val existingSimCards: List<SimCard>, val newSimCard: SimCard? = null
    ) : DashboardUIState(existingSimCards)
}
