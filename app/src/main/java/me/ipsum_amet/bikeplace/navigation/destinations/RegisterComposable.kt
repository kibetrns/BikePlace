package me.ipsum_amet.bikeplace.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import me.ipsum_amet.bikeplace.Util.Action
import me.ipsum_amet.bikeplace.Util.REGISTER_SCREEN

fun NavGraphBuilder.registerComposable(
    navigateToSignUpScreen: (Action) -> Unit
) {
    composable(route = REGISTER_SCREEN) {

    }
}