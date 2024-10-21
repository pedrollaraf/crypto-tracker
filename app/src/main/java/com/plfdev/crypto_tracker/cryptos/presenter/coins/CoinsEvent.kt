package com.plfdev.crypto_tracker.cryptos.presenter.coins

import com.plfdev.crypto_tracker.core.domain.util.NetworkError

sealed interface CoinsEvent {
    data class Error(val error: NetworkError): CoinsEvent
}