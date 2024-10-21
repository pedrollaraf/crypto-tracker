package com.plfdev.crypto_tracker.cryptos.presenter.models

import androidx.annotation.DrawableRes
import com.plfdev.crypto_tracker.cryptos.domain.Coin
import com.plfdev.crypto_tracker.core.presenter.util.getDrawableIdForCoin
import java.text.NumberFormat
import java.util.Locale

data class CoinUi(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: DisplayableDecimalNumber,
    val priceUsd: DisplayableDecimalNumber,
    val changePercent24hr: DisplayableDecimalNumber,
    @DrawableRes val iconRes:Int
)

data class DisplayableDecimalNumber(
    val value: Double,
    val formatted: String,
)

fun Coin.toCoinUi(): CoinUi {
    return CoinUi(
        id = id,
        name = name,
        symbol = symbol,
        rank = rank,
        priceUsd = priceUsd.toDisplayableNumber(),
        marketCapUsd = marketCapUsd.toDisplayableNumber(),
        changePercent24hr = changePercent24hr.toDisplayableNumber(),
        iconRes = getDrawableIdForCoin(symbol = symbol)
    )
}

fun Double.toDisplayableNumber(): DisplayableDecimalNumber {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }
    return DisplayableDecimalNumber(
        value = this,
        formatted = formatter.format(this)
    )
}
