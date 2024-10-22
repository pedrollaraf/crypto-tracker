package com.plfdev.crypto_tracker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plfdev.crypto_tracker.core.presenter.util.ObserveAsEvents
import com.plfdev.crypto_tracker.core.presenter.util.toString
import com.plfdev.crypto_tracker.cryptos.presenter.coin_detail.CoinDetailScreen
import com.plfdev.crypto_tracker.cryptos.presenter.coins.CoinsEvent
import com.plfdev.crypto_tracker.cryptos.presenter.coins.CoinsViewModel
import com.plfdev.crypto_tracker.cryptos.presenter.coins.screen.CoinsScreen
import com.plfdev.crypto_tracker.ui.theme.CryptoTrackerTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel = koinViewModel<CoinsViewModel>()
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
                    when {
                        state.selectedCoin != null -> CoinDetailScreen(
                            state = state,
                            modifier = Modifier.padding(innerPadding)
                        ) else -> CoinsScreen(
                            modifier = Modifier.padding(innerPadding),
                            state = state,
                            onAction = viewModel::onAction
                        )
                    }

                }
            }
        }
    }
}