@file:OptIn(ExperimentalLayoutApi::class)

package com.plfdev.crypto_tracker.cryptos.presenter.coin_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plfdev.crypto_tracker.R
import com.plfdev.crypto_tracker.cryptos.presenter.coin_detail.components.InfoCard
import com.plfdev.crypto_tracker.cryptos.presenter.coins.components.previewCoin
import com.plfdev.crypto_tracker.cryptos.presenter.coins.screen.CoinsState
import com.plfdev.crypto_tracker.cryptos.presenter.models.toDisplayableNumber
import com.plfdev.crypto_tracker.ui.theme.CryptoTrackerTheme
import com.plfdev.crypto_tracker.ui.theme.greenBackground

@Composable
fun CoinDetailScreen(
    modifier: Modifier = Modifier,
    state: CoinsState
) {
    val contentColor = if(isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }
    if(state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if(state.selectedCoin != null) {
        val coin = state.selectedCoin

        Column(
            modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = coin.iconRes),
                contentDescription = coin.name,
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = coin.name,
                fontSize = 40.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center,
                color = contentColor
            )
            Text(
                text = coin.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center,
                color = contentColor
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                InfoCard(
                    title = stringResource(id = R.string.market_cap),
                    formattedText = "$ ${coin.marketCapUsd.formatted}",
                    icon = ImageVector.vectorResource(R.drawable.stock)
                )
                InfoCard(
                    title = stringResource(id = R.string.price),
                    formattedText = "$ ${coin.priceUsd.formatted}",
                    icon = ImageVector.vectorResource(R.drawable.dollar)
                )
                val absoluteChangeFormatted = (coin.priceUsd.value * (coin.changePercent24Hr.value / 100)).toDisplayableNumber()
                val isPositive = coin.changePercent24Hr.value > 0.0
                val changeLast24hColor = if(isPositive) {
                    if(isSystemInDarkTheme()) {
                        Color.Green
                    } else {
                        greenBackground
                    }
                } else {
                    MaterialTheme.colorScheme.error
                }
                InfoCard(
                    title = stringResource(id = R.string.change_last_24h),
                    formattedText = "$ ${absoluteChangeFormatted.formatted}",
                    icon = if(isPositive) ImageVector.vectorResource(R.drawable.trending) else ImageVector.vectorResource(R.drawable.trending_down),
                    contentColor = changeLast24hColor
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CoinDetailScreenPreview() {
    CryptoTrackerTheme {
        CoinDetailScreen(
            state = CoinsState(
               selectedCoin = previewCoin
            ),
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}