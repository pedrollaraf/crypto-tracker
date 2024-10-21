package com.plfdev.crypto_tracker.cryptos.domain

import com.plfdev.crypto_tracker.core.domain.util.NetworkError
import com.plfdev.crypto_tracker.core.domain.util.Result

interface CoinDataSource { //Can be CoinRepository name
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}