package com.plfdev.crypto_tracker.cryptos.presenter.coins.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.plfdev.crypto_tracker.cryptos.presenter.coins.CoinsAction
import com.plfdev.crypto_tracker.cryptos.presenter.coins.components.CoinListItem
import com.plfdev.crypto_tracker.cryptos.presenter.coins.components.previewCoin
import com.plfdev.crypto_tracker.ui.theme.CryptoTrackerTheme

@Composable
fun CoinsScreen(
    state: CoinsState,
    onAction: (CoinsAction) -> Unit,
    modifier: Modifier = Modifier
) {
    if(state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
        ) {
            items(state.coins) { coinUi ->
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
}

@PreviewLightDark
@Composable
private fun CoinsScreenPreview() {
    CryptoTrackerTheme {
        CoinsScreen(
            state = CoinsState(
                coins = (1..100).map {
                    previewCoin.copy(id = it.toString())
                }
            ),
            onAction = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}