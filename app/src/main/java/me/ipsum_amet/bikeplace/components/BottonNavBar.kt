package me.ipsum_amet.bikeplace.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import me.ipsum_amet.bikeplace.R
@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavBarScreen.Home,
        BottomNavBarScreen.Search,
        BottomNavBarScreen.Statistics
    )
    BottomNavigation {
        val navBackStackEntry by  navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        val bottomDestination = items.any { it.route == currentDestination?.route }
        if (bottomDestination) {
            BottomNavigation() {
                items.forEach { bottomNavBarScreen: BottomNavBarScreen ->
                    AddItem(
                        screen = bottomNavBarScreen,
                        currentDestination = currentDestination,
                        navController = navController
                    )
                }
            }
        }
    }
}
@Composable
fun RowScope.AddItem(
    screen: BottomNavBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController ) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = stringResource(id = R.string.navigation_icon)
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route) {
                launchSingleTop = true
            }
        },
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
    )
}