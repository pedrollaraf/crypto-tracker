package com.plfdev.crypto_tracker.cryptos.presenter.coins.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.plfdev.crypto_tracker.core.data.networking.RequestStates
import com.plfdev.crypto_tracker.core.presenter.util.toString
import com.plfdev.crypto_tracker.cryptos.presenter.coins.CoinsAction
import com.plfdev.crypto_tracker.cryptos.presenter.coins.components.CoinListItem
import com.plfdev.crypto_tracker.cryptos.presenter.coins.components.LoadingDotsWithAnimation
import com.plfdev.crypto_tracker.cryptos.presenter.coins.components.previewCoin
import com.plfdev.crypto_tracker.cryptos.presenter.models.CoinUi
import com.plfdev.crypto_tracker.ui.theme.CryptoTrackerTheme

@Composable
fun CoinsScreen(
    state: CoinsState,
    onAction: (CoinsAction) -> Unit,
    modifier: Modifier = Modifier
) {
    when (state.requestStates) {
        is RequestStates.Loading, is RequestStates.Initial -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadingDotsWithAnimation(
                    numDots = 4,
                    circleSize = 16.dp,
                    circleColor = MaterialTheme.colorScheme.primary
                )
            }
        }
        is RequestStates.Success<List<CoinUi>> -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
            ) {
                items(state.requestStates.data) { coinUi ->
                    CoinListItem(
                        coinUi = coinUi,
                        onClick = {
                            onAction(CoinsAction.OnCoinClick(coinUi = coinUi))
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    HorizontalDivider()
                }
            }
        }
        is RequestStates.Failure -> {
            val error = state.requestStates.error!!
            Column (
                modifier = modifier.fillMaxSize().padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    error.toString(LocalContext.current),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    onClick = {
                        onAction(CoinsAction.OnRefresh)
                    }
                ) {
                    Text("Try Again")
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CoinsScreenPreview() {
    CryptoTrackerTheme {
        CoinsScreen(
            state = CoinsState(
                requestStates = RequestStates.Success((1..100).map {
                    previewCoin.copy(id = it.toString())
                })
            ),
            onAction = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}