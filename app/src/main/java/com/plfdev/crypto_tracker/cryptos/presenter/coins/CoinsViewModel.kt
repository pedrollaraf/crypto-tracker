package com.plfdev.crypto_tracker.cryptos.presenter.coins

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plfdev.crypto_tracker.core.domain.util.onError
import com.plfdev.crypto_tracker.core.domain.util.onSuccess
import com.plfdev.crypto_tracker.cryptos.domain.CoinDataSource
import com.plfdev.crypto_tracker.cryptos.presenter.coin_detail.chart.DataPoint
import com.plfdev.crypto_tracker.cryptos.presenter.coins.screen.CoinsState
import com.plfdev.crypto_tracker.cryptos.presenter.models.CoinUi
import com.plfdev.crypto_tracker.cryptos.presenter.models.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class CoinsViewModel(
    private val coinDataSource: CoinDataSource
): ViewModel() {

    private val _state = MutableStateFlow(CoinsState())
    val state = _state.onStart {
        loadCoins() //Fetch initial data from here because we will have more control when flow starts
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        CoinsState()
    )

    private val _events = Channel<CoinsEvent>()//WE ARE CREATING A CHANNEL HERE BECAUSE WE GONNA SEND ERROR MESSAGE BY TOAST
    val events = _events.receiveAsFlow()//IF WE WILL CHANGE THE VIEW STATE YOU CAN SET IN COINS_STATE

    private fun loadCoins() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            coinDataSource.getCoins().onSuccess { coins ->
                _state.update {
                    it.copy(
                        coins = coins.map { coin ->
                            coin.toCoinUi()
                        },
                        isLoading = false
                    )
                }
            }.onError { error ->
                _events.send(CoinsEvent.Error(error))
            }
        }
    }

    fun onAction(action: CoinsAction) {
        when(action) {
            is CoinsAction.OnCoinClick -> {
                selectCoin(action.coinUi)
            }
//            is CoinsAction.OnRefresh -> {
//                loadCoins()
//            }
        }
    }

    private fun selectCoin(coinUi: CoinUi) {
        _state.update {
            it.copy(
                selectedCoin = coinUi
            )
        }

        viewModelScope.launch {
            coinDataSource.getCoinHistory(
                coinId = coinUi.id,
                start = ZonedDateTime.now().minusDays(5),
                end = ZonedDateTime.now()
            ).onSuccess { history ->
                val dataPoints = history.sortedBy { it.dateTime }.map {
                    DataPoint(
                        x =  it.dateTime.hour.toFloat(),
                        y = it.priceUsd.toFloat(),
                        xLabel = DateTimeFormatter.ofPattern("ha\nM/d").format(it.dateTime)
                    )
                }

                _state.update {
                    it.copy(
                        selectedCoin = it.selectedCoin?.copy(
                            coinPriceHistory = dataPoints
                        )
                    )
                }
            }.onError { error ->
                _events.send(CoinsEvent.Error(error))
            }
        }
    }


}