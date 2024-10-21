package com.plfdev.crypto_tracker.cryptos.data.networking

import com.plfdev.crypto_tracker.core.data.networking.constructUrl
import com.plfdev.crypto_tracker.core.data.networking.safeCall
import com.plfdev.crypto_tracker.core.domain.util.NetworkError
import com.plfdev.crypto_tracker.core.domain.util.Result
import com.plfdev.crypto_tracker.core.domain.util.map
import com.plfdev.crypto_tracker.cryptos.data.dto.CoinsResponseDto
import com.plfdev.crypto_tracker.cryptos.data.mappers.toCoin
import com.plfdev.crypto_tracker.cryptos.domain.Coin
import com.plfdev.crypto_tracker.cryptos.domain.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteCoinDataSource(
    private val httpClient: HttpClient
): CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { response ->
            response.data.map {
                it.toCoin()
            }
        }
    }
}