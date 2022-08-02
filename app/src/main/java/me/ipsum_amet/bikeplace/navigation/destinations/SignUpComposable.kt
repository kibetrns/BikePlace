package me.ipsum_amet.bikeplace.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import me.ipsum_amet.bikeplace.Util.Action
import me.ipsum_amet.bikeplace.Util.SIGNUP_ARGUMENT_KEY
import me.ipsum_amet.bikeplace.Util.SIGNUP_SCREEN

fun NavGraphBuilder.signUpComposable(
    navigateToListScreen: (Action) -> Unit
) {
    composable(
        route = SIGNUP_SCREEN,
        arguments = listOf(navArgument(SIGNUP_ARGUMENT_KEY){
            type = NavType.StringType
        })
    ) {

    }
}

