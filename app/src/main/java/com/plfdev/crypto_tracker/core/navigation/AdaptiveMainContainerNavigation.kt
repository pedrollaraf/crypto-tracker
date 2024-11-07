@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)

package com.plfdev.crypto_tracker.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.BackNavigationBehavior
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.plfdev.crypto_tracker.cryptos.presenter.coin_detail.CoinDetailScreen
import com.plfdev.crypto_tracker.cryptos.presenter.coins.CoinsAction
import com.plfdev.crypto_tracker.cryptos.presenter.coins.CoinsViewModel
import com.plfdev.crypto_tracker.cryptos.presenter.coins.screen.CoinsScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun AdaptiveMainContainerNavigation(
    modifier: Modifier = Modifier,
    viewModel: CoinsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    //val context = LocalContext.current


    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

    val layoutType = calculateFromAdaptiveInfo(
        windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    )

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()

    val navigationSuiteItemColors = NavigationSuiteDefaults.itemColors(
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.primary,
            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
            unselectedIconColor = MaterialTheme.colorScheme.onBackground
        ),
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
            unselectedIconColor = MaterialTheme.colorScheme.onBackground,
            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
        ),
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.primary,
            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
            unselectedIconColor = MaterialTheme.colorScheme.onBackground
        ),
    )

//    ObserveAsEvents(events = viewModel.events) { event ->
//        when(event) {
//            is CoinsEvent.Error -> {
//                Toast.makeText(
//                    context,
//                    event.error.toString(context),
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        }
//    }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach { screens ->
                val isSelected = screens == currentDestination
                item(
                    modifier = Modifier.height(45.dp),
                    colors = navigationSuiteItemColors,
                    selected = isSelected,
                    icon = {
                        Icon(
                            imageVector = if(isSelected)
                                screens.selectedIcon
                            else screens.unselectedIcon,
                            contentDescription = null,
                            modifier = Modifier.size(28.dp)
                        )
                    },
                    label = {
                        if(layoutType == NavigationSuiteType.NavigationDrawer) {
                            Text(screens.title)
                        }
                    },
                    onClick = {
                        currentDestination = screens
                    },
                )
            }
        },
        layoutType = layoutType
    ) {
        when (currentDestination) {
            AppDestinations.HOME ->  NavigableListDetailPaneScaffold(
                modifier = modifier,
                navigator = navigator,
                listPane = {
                    AnimatedPane {
                        CoinsScreen(
                            state = state,
                            onAction = { action ->
                                when(action) {
                                    is CoinsAction.OnCoinClick -> {
                                        viewModel.onAction(action)
                                        navigator.navigateTo(
                                            pane = ListDetailPaneScaffoldRole.Detail
                                        )
                                    }
                                    is CoinsAction.OnRefresh -> {
                                        viewModel.onAction(action)
                                    }
                                }
                            }
                        )
                    }
                },
                detailPane = {
                    AnimatedPane {
                        CoinDetailScreen(
                            state = state,

                        )
                    }
                },
            )
            AppDestinations.FAVORITES -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Favorites")
                }
            }
            AppDestinations.PROFILE -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Profile")
                }
            }
        }
    }
}

private fun calculateFromAdaptiveInfo(windowSizeClass: WindowSizeClass): NavigationSuiteType {
    return if(windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED
        && windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.EXPANDED) {
        NavigationSuiteType.NavigationDrawer
    } else if(windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT
        && (windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.EXPANDED ||
                windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.MEDIUM)) {
        NavigationSuiteType.NavigationBar
    } else {
        NavigationSuiteType.NavigationRail
    }
}