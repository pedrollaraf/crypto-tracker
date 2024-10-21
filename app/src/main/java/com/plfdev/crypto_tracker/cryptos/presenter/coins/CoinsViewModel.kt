package com.plfdev.crypto_tracker.cryptos.presenter.coins

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plfdev.crypto_tracker.core.domain.util.onError
import com.plfdev.crypto_tracker.core.domain.util.onSuccess
import com.plfdev.crypto_tracker.cryptos.domain.CoinDataSource
import com.plfdev.crypto_tracker.cryptos.presenter.coins.screen.CoinsState
import com.plfdev.crypto_tracker.cryptos.presenter.models.toCoinUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

    private fun loadCoins() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            coinDataSource.getCoins().onSuccess { coins ->
                _state.update { it.copy(
                    coins = coins.map { coin ->
                        coin.toCoinUi()
                    },
                    isLoading = false)
                }
            }.onError { error ->
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onAction(action: CoinsAction) {
        when(action) {
            is CoinsAction.OnCoinClick -> {

            }
//            is CoinsAction.OnRefresh -> {
//                loadCoins()
//            }
        }
    }

}