package com.plfdev.crypto_tracker.cryptos.data.mappers

import com.plfdev.crypto_tracker.cryptos.data.dto.CoinDto
import com.plfdev.crypto_tracker.cryptos.data.dto.CoinPriceDto
import com.plfdev.crypto_tracker.cryptos.domain.Coin
import com.plfdev.crypto_tracker.cryptos.domain.CoinPrice
import java.time.Instant
import java.time.ZoneId

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr
    )
    //IF YOU HAVE A TIMEZONE STRING AND PARSE TO ANOTHER TYPE
    //IT SHOULD BE PARSE HERE WHEN YOU GET TIMEZONE STRING FROM COIN_DTO TO COIN TIMEZONE OBJECT
}

fun CoinPriceDto.toCoinPrice(): CoinPrice {
    return CoinPrice(
        priceUsd = priceUsd,
        dateTime = Instant.ofEpochMilli(time).atZone(ZoneId.of("UTC"))
    )
}