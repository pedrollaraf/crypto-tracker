package com.plfdev.crypto_tracker.di

import com.plfdev.crypto_tracker.core.data.networking.HttpClientFactory
import com.plfdev.crypto_tracker.cryptos.data.networking.RemoteCoinDataSource
import com.plfdev.crypto_tracker.cryptos.domain.CoinDataSource
import com.plfdev.crypto_tracker.cryptos.presenter.coins.CoinsViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClientFactory.create(CIO.create())
    }
    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()

    viewModelOf(::CoinsViewModel)
}