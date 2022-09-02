package me.ipsum_amet.bikeplace.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import me.ipsum_amet.bikeplace.Util.*
import me.ipsum_amet.bikeplace.view.list.ListScreen
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

fun NavGraphBuilder.listComposable(
    navigateToBikeScreen: (String) -> Unit,
    bikePlaceViewModel: BikePlaceViewModel
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
    val action = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()

    LaunchedEffect(key1 = action) {
        bikePlaceViewModel.action.value = action
    }
        ListScreen(navigateToBikeScreen = navigateToBikeScreen, bikePlaceViewModel = bikePlaceViewModel)
    }
}