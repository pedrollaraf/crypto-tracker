package com.plfdev.crypto_tracker.cryptos.data.mappers

import com.plfdev.crypto_tracker.cryptos.data.dto.CoinDto
import com.plfdev.crypto_tracker.cryptos.domain.Coin

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