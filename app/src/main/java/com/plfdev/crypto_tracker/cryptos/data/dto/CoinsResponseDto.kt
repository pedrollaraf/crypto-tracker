package com.plfdev.crypto_tracker.cryptos.data.dto

import kotlinx.serialization.Serializable

@Serializable //Serializable data from API
data class CoinsResponseDto(
    val data : List<CoinDto>
)
