package me.ipsum_amet.bikeplace.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import me.ipsum_amet.bikeplace.Util.Action
import me.ipsum_amet.bikeplace.Util.LIST_ARGUMENT_KEY
import me.ipsum_amet.bikeplace.Util.LIST_SCREEN
import me.ipsum_amet.bikeplace.Util.REGISTER_SCREEN

fun NavGraphBuilder.listComposable(
    navigateToBikeScreen: (Int) -> Unit
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) {

    }
}