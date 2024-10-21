package com.plfdev.crypto_tracker.cryptos.data.dto

import kotlinx.serialization.Serializable

@Serializable //Serializable data from API
data class CoinDto(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: Double,
    val priceUsd: Double,
    val changePercent24Hr: Double,
)
