package com.apollovisa.etoken.data.repository

import android.content.SharedPreferences
import com.apollovisa.etoken.domain.models.SimCard
import com.apollovisa.etoken.domain.repository.SimCardRepository

class SharedPreferenceSimCardRepository(
    val sharedPreferences: SharedPreferences
) : SimCardRepository {

    companion object {
        const val SLOT_INDEX = "SLOT_INDEX"
        const val SLOT_NAME = "SLOT_NAME"
        const val PHONE_NUMBER = "PHONE_NUMBER"
    }

    override suspend fun add(simCard: SimCard) {
        sharedPreferences.edit().apply {
            putInt("$SLOT_INDEX${simCard.slotIndex}", simCard.slotIndex)
            putString("$SLOT_NAME${simCard.slotIndex}", simCard.slotName)
            putString("$PHONE_NUMBER${simCard.slotIndex}", simCard.phoneNumber)
        }.apply()
    }

    override suspend fun getSimCardBySlotIndex(slotIndex: Int): SimCard? {
        val slotIndex = sharedPreferences.getInt("$SLOT_INDEX$slotIndex", -1)
        val slotName = sharedPreferences.getString("$SLOT_NAME$slotIndex", null)
        val phoneNumber = sharedPreferences.getString("$PHONE_NUMBER$slotIndex", null)

        if (slotIndex == -1 || slotName == null || phoneNumber == null) return null

        return SimCard(
            slotIndex = slotIndex,
            slotName = slotName,
            phoneNumber = phoneNumber
        )
    }

    override suspend fun getSimCards(): List<SimCard> {
        return (0..2)
            .map {slotIndex ->
                getSimCardBySlotIndex(slotIndex)
            }
            .filterNotNull()
    }

    override suspend fun update(simCard: SimCard) {
        sharedPreferences.edit().apply {
            putInt("$SLOT_INDEX${simCard.slotIndex}", simCard.slotIndex)
            putString("$SLOT_NAME${simCard.slotIndex}", simCard.slotName)
            putString("$PHONE_NUMBER${simCard.slotIndex}", simCard.phoneNumber)
        }.apply()
    }

    override suspend fun delete(slotIndex: Int) {
        sharedPreferences.edit().apply {
            remove("$SLOT_INDEX$slotIndex")
            remove("$SLOT_NAME$slotIndex")
            remove("$PHONE_NUMBER$slotIndex")
        }.apply()
    }
}
