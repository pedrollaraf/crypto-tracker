package com.plfdev.crypto_tracker.cryptos.data.dto

import kotlinx.serialization.Serializable

@Serializable //Serializable data from API
data class CoinHistoryDto(
    val data : List<CoinPriceDto>
)