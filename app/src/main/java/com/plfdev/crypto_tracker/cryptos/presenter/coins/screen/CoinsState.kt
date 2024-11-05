package com.plfdev.crypto_tracker.cryptos.presenter.coins.screen

import androidx.compose.runtime.Immutable
import com.plfdev.crypto_tracker.core.data.networking.RequestStates
import com.plfdev.crypto_tracker.cryptos.presenter.models.CoinUi

@Immutable //The data class it will never change. You will work only with copies of this data class
data class CoinsState(
    val selectedCoin: CoinUi? = null,
    val requestStates: RequestStates<List<CoinUi>> = RequestStates.Initial
)