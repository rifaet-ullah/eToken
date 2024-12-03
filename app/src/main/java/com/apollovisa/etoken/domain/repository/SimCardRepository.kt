package com.apollovisa.etoken.domain.repository

import com.apollovisa.etoken.domain.models.SimCard

interface SimCardRepository {
    suspend fun add(simCard: SimCard)

    suspend fun getSimCardBySlotIndex(slotIndex: Int): SimCard?

    suspend fun getSimCards(): List<SimCard>

    suspend fun update(simCard: SimCard)

    suspend fun delete(slotIndex: Int)
}
