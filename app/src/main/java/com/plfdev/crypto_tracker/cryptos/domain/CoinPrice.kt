package com.plfdev.crypto_tracker.cryptos.domain

import java.time.ZonedDateTime

data class CoinPrice(
    val priceUsd: Double,
    val dateTime: ZonedDateTime,
)
