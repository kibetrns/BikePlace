package me.ipsum_amet.bikeplace.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import me.ipsum_amet.bikeplace.Util.*
import me.ipsum_amet.bikeplace.view.home.HomeScreen
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

fun NavGraphBuilder.homeComposable(
    bikePlaceViewModel: BikePlaceViewModel,
    navigateToBikeDetailsScreen: (bikeId: String) -> Unit,
) {
    composable(
        route = HOME_SCREEN,
        arguments = listOf(
            navArgument(HOME_SCREEN_ARGUMENT_KEY_1){
                defaultValue = Action.NO_ACTION.toString()
                type = NavType.StringType
            },
            navArgument(HOME_SCREEN_ARGUMENT_KEY_2) {
                type = NavType.StringType
                nullable = true
            }
        )
    ) { navBackStackEntry ->
        val action = navBackStackEntry.arguments?.getString(HOME_SCREEN_ARGUMENT_KEY_1).toAction()
        val bikeId = navBackStackEntry.arguments?.getString(HOME_SCREEN_ARGUMENT_KEY_2).toString()

        val selectedBike by bikePlaceViewModel.selectedBike.collectAsState()

        LaunchedEffect(key1 = bikeId) {
            bikePlaceViewModel.getSelectedBike(bikeId = bikeId)
        }

        LaunchedEffect(key1 = action) {
            bikePlaceViewModel.action.value = action
        }


        HomeScreen(
            bikePlaceViewModel = bikePlaceViewModel,
            onCHomeBikeClicked = {},
            navigateToTopChoiceBikes = {},
            navigateToBikeDetailsScreen = navigateToBikeDetailsScreen
        )
    }
}