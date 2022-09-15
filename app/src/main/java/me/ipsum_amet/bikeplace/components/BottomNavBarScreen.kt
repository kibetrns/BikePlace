package me.ipsum_amet.bikeplace.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavBarScreen(
        route = "home",
        title = "HOME",
        icon = Icons.Default.Dashboard
    )
    object Search :  BottomNavBarScreen(
        route = "search",
        title = "SEARCH",
        icon = Icons.Default.Search
    )
    object Statistics : BottomNavBarScreen(
        route = "statistics",
        title = "STATISTICS",
        icon = Icons.Default.StackedLineChart
    )

}