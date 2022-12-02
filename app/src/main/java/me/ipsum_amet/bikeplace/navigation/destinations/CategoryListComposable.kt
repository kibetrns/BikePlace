package me.ipsum_amet.bikeplace.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import me.ipsum_amet.bikeplace.util.*
import me.ipsum_amet.bikeplace.view.list.ListScreen
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

fun NavGraphBuilder.categoryListComposable(
    bikePlaceViewModel: BikePlaceViewModel,
    navigateToBikeDetailsScreen: (bikeId: String) -> Unit
) {
    composable(
        route = CATEGORY_LIST_SCREEN,
        arguments = listOf(
            navArgument(CATEGORY_LIST_SCREEN_ARGUMENT){
                type = NavType.StringType
            }
        )
    ) { navBackStackEntry ->

        val action = navBackStackEntry.arguments?.getString(CATEGORY_LIST_SCREEN_ARGUMENT).toAction()

        LaunchedEffect(key1 = true) {
            bikePlaceViewModel.action.value = action
        }

        ListScreen(navigateToBikeScreen = navigateToBikeDetailsScreen, bikePlaceViewModel = bikePlaceViewModel)
    }

}