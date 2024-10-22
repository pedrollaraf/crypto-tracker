package com.plfdev.crypto_tracker.cryptos.domain

import java.time.ZonedDateTime

data class CoinPrice(
    private val priceUsd: Double,
    val dateTime: ZonedDateTime,
)
