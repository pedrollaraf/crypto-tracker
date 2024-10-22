package com.plfdev.crypto_tracker.cryptos.domain

import com.plfdev.crypto_tracker.core.domain.util.NetworkError
import com.plfdev.crypto_tracker.core.domain.util.Result
import java.time.ZonedDateTime

interface CoinDataSource { //Can be CoinRepository name
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
    suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError>
}