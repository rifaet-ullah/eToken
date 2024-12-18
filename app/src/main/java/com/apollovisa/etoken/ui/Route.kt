package com.apollovisa.etoken.ui

import kotlinx.serialization.Serializable

sealed class Route {
    @Serializable
    object Splash : Route()

    @Serializable
    object LogIn : Route()

    @Serializable
    object Dashboard : Route()

    @Serializable
    data class SimCard(val slotIndex: Int) : Route()
}
