@file:OptIn(ExperimentalLayoutApi::class)

package com.plfdev.crypto_tracker.cryptos.presenter.coin_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plfdev.crypto_tracker.R
import com.plfdev.crypto_tracker.core.data.networking.RequestStates
import com.plfdev.crypto_tracker.cryptos.presenter.coin_detail.chart.ChartStyle
import com.plfdev.crypto_tracker.cryptos.presenter.coin_detail.chart.DataPoint
import com.plfdev.crypto_tracker.cryptos.presenter.coin_detail.chart.LineChart
import com.plfdev.crypto_tracker.cryptos.presenter.coin_detail.components.InfoCard
import com.plfdev.crypto_tracker.cryptos.presenter.coins.components.LoadingDotsWithAnimation
import com.plfdev.crypto_tracker.cryptos.presenter.coins.components.previewCoin
import com.plfdev.crypto_tracker.cryptos.presenter.coins.screen.CoinsState
import com.plfdev.crypto_tracker.cryptos.presenter.models.toDisplayableNumber
import com.plfdev.crypto_tracker.ui.theme.CryptoTrackerTheme
import com.plfdev.crypto_tracker.ui.theme.SpaceMono
import com.plfdev.crypto_tracker.ui.theme.greenBackground

@Composable
fun CoinDetailScreen(
    modifier: Modifier = Modifier,
    state: CoinsState,
    onBack: () -> Unit,
    onFavorite: (Boolean) -> Unit,
) {
    val contentColor = if(isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }
    if(state.requestStates is RequestStates.Loading) {
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
    } else if(state.selectedCoin != null) {
        val coin = state.selectedCoin

        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 32.dp),
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
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center,
                    color = contentColor,
                    modifier = Modifier.padding(vertical = 16.dp),
                    style = TextStyle(
                        lineHeight = 38.sp,
                        fontFamily = SpaceMono
                    )
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

                AnimatedVisibility(
                    visible = coin.coinPriceHistory.isNotEmpty()
                ) {
                    var selectedDataPoint by remember {
                        mutableStateOf<DataPoint?>(coin.coinPriceHistory.first())
                    }

                    var labelWidth by remember {
                        mutableFloatStateOf(0f)
                    }
                    var totalChartWidth by remember {
                        mutableFloatStateOf(0f)
                    }
                    val amountOfVisibleDataPoints = if(labelWidth > 0) {
                        ((totalChartWidth - 2.5 * labelWidth) / labelWidth).toInt()
                    } else { 0 }

                    val startIndex = (coin.coinPriceHistory.lastIndex - amountOfVisibleDataPoints).coerceAtLeast(0)

                    LineChart(
                        dataPoints = coin.coinPriceHistory,
                        style = ChartStyle(
                            chartLineColor = MaterialTheme.colorScheme.primary,
                            unselectedColor = MaterialTheme.colorScheme.secondary.copy(
                                alpha = 0.6f
                            ),
                            selectedColor = MaterialTheme.colorScheme.primary,
                            helperLinesThicknessPx = 5f,
                            axisLinesThicknessPx = 5f,
                            labelFontSize = 14.sp,
                            minYLabelSpacing = 25.dp,
                            verticalPadding = 8.dp,
                            horizontalPadding = 8.dp,
                            xAxisLabelSpacing = 8.dp
                        ),
                        visibleDataPointsIndices = startIndex..coin.coinPriceHistory.lastIndex,
                        unit = "$",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16 / 9f)
                            .onSizeChanged {
                                totalChartWidth = it.width.toFloat()
                            },
                        selectedDataPoint = selectedDataPoint,
                        onSelectedDataPoint = {
                            selectedDataPoint = it
                        },
                        onXLabelWidthChange = { labelWidth = it }
                    )
                }
            }
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(R.string.voltar),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            IconButton(
                onClick = {

                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp),
            ) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = stringResource(R.string.voltar),
                    tint = MaterialTheme.colorScheme.onBackground
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
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            onBack = {},
            onFavorite = {}
        )
    }
}