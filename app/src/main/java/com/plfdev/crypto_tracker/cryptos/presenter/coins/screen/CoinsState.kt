package com.plfdev.crypto_tracker.cryptos.presenter.coins.screen

import androidx.compose.runtime.Immutable
import com.plfdev.crypto_tracker.cryptos.presenter.models.CoinUi

@Immutable //The data class it will never change. You will work only with copies of this data class
data class CoinsState(
    val isLoading: Boolean = false,
    val coins: List<CoinUi> = emptyList(),
    val selectedCoin: CoinUi? = null
)
