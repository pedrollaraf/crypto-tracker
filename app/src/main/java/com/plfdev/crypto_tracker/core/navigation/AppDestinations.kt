package com.plfdev.crypto_tracker.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

enum class AppDestinations(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    HOME(
        title = "Home",
        selectedIcon = Icons.Default.Home,
        unselectedIcon = Icons.Outlined.Home,
    ),
    FAVORITES(
        title = "Favorites",
        selectedIcon = Icons.Default.Favorite,
        unselectedIcon = Icons.Outlined.FavoriteBorder,
    ),
    PROFILE(
        title = "Profile",
        selectedIcon = Icons.Default.Person,
        unselectedIcon = Icons.Outlined.Person,
    ),
}