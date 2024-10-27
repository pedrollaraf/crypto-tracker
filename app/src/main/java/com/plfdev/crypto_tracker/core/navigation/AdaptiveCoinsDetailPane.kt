@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.plfdev.crypto_tracker.core.navigation

import android.widget.Toast
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plfdev.crypto_tracker.core.presenter.util.ObserveAsEvents
import com.plfdev.crypto_tracker.core.presenter.util.toString
import com.plfdev.crypto_tracker.cryptos.presenter.coin_detail.CoinDetailScreen
import com.plfdev.crypto_tracker.cryptos.presenter.coins.CoinsAction
import com.plfdev.crypto_tracker.cryptos.presenter.coins.CoinsEvent
import com.plfdev.crypto_tracker.cryptos.presenter.coins.CoinsViewModel
import com.plfdev.crypto_tracker.cryptos.presenter.coins.screen.CoinsScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun AdaptiveCoinsDetailPane(
    modifier: Modifier = Modifier,
    viewModel: CoinsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvents(events = viewModel.events) { event ->
        when(event) {
            is CoinsEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.toString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()

    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                CoinsScreen(
                    state = state,
                    onAction = { action ->
                        viewModel.onAction(action)
                        when(action) {
                            is CoinsAction.OnCoinClick -> {
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Detail
                                )
                            }
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                CoinDetailScreen(state = state)
            }
        },
        modifier = modifier
    )
}