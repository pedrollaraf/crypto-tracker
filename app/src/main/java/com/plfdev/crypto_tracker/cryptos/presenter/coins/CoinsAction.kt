package com.plfdev.crypto_tracker.cryptos.presenter.coins

import com.plfdev.crypto_tracker.cryptos.presenter.models.CoinUi

sealed interface CoinsAction {
    data class OnCoinClick(val coinUi: CoinUi): CoinsAction
    //data object OnRefresh:CoinsAction
}