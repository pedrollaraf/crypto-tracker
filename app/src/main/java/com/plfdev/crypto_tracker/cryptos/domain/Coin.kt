package com.plfdev.crypto_tracker.cryptos.domain

import kotlinx.serialization.Serializable

data class Coin(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: Double,
    val priceUsd: Double,
    val changePercent24hr: Double,
)
