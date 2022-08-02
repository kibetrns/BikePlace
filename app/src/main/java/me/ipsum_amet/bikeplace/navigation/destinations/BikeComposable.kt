package me.ipsum_amet.bikeplace.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import me.ipsum_amet.bikeplace.Util.Action
import me.ipsum_amet.bikeplace.Util.BIKE_ARGUMENT_KEY
import me.ipsum_amet.bikeplace.Util.BIKE_SCREEN

fun NavGraphBuilder.bikeComposable(
    navigateToListScreen: (Action) -> Unit
) {
    composable(
        route = BIKE_SCREEN,
        arguments = listOf(navArgument(BIKE_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) {

    }
}