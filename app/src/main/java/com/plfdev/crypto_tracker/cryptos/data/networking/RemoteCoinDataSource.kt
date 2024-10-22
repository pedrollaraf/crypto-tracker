package com.plfdev.crypto_tracker.cryptos.data.networking

import android.os.Build
import androidx.annotation.RequiresApi
import com.plfdev.crypto_tracker.core.data.networking.constructUrl
import com.plfdev.crypto_tracker.core.data.networking.safeCall
import com.plfdev.crypto_tracker.core.domain.util.NetworkError
import com.plfdev.crypto_tracker.core.domain.util.Result
import com.plfdev.crypto_tracker.core.domain.util.map
import com.plfdev.crypto_tracker.cryptos.data.dto.CoinHistoryDto
import com.plfdev.crypto_tracker.cryptos.data.dto.CoinsResponseDto
import com.plfdev.crypto_tracker.cryptos.data.mappers.toCoin
import com.plfdev.crypto_tracker.cryptos.data.mappers.toCoinPrice
import com.plfdev.crypto_tracker.cryptos.domain.Coin
import com.plfdev.crypto_tracker.cryptos.domain.CoinDataSource
import com.plfdev.crypto_tracker.cryptos.domain.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

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

    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime,
    ): Result<List<CoinPrice>, NetworkError> {
        val startMillis = start.withZoneSameInstant(
            ZoneId.of("UTC")
        ).toInstant().toEpochMilli()

        val endMillis = end.withZoneSameInstant(
            ZoneId.of("UTC")
        ).toInstant().toEpochMilli()

        return safeCall<CoinHistoryDto> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history")
            ) {
                parameter("interval", "h6")
                parameter("start",startMillis)
                parameter("end",endMillis)
            }
        }.map { response ->
            response.data.map {coinPriceDto ->
                coinPriceDto.toCoinPrice()
            }
        }
    }
}