package me.ipsum_amet.bikeplace.navigation.graphs

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.*
import androidx.navigation.compose.composable
import me.ipsum_amet.bikeplace.Util.Action
import me.ipsum_amet.bikeplace.Util.toAction
import me.ipsum_amet.bikeplace.view.bikeEntry.BikeScreen
import me.ipsum_amet.bikeplace.view.list.ListScreen
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

fun NavGraphBuilder.searchNavGraph(navController: NavHostController, bikePlaceViewModel: BikePlaceViewModel) {
    navigation(
        route = Graph.SEARCH,
        startDestination = SearchScreen.Search.route
    ) {
        composable(
            route = SearchScreen.Search.route
        ) {
            ListScreen(
                navigateToBikeScreen = { bikeId: String ->
                    navController.navigate("bikeEntry/$bikeId") {
                        popUpTo(SearchScreen.Search.route)
                        launchSingleTop = true
                    }
                },
                bikePlaceViewModel = bikePlaceViewModel,
                navController = navController
            )
        }

        composable(
            route = SearchScreen.BikeEntry.route,
            arguments = listOf(navArgument("bikeId") {
                type = NavType.StringType
            })
        ) { navBackStackEntry: NavBackStackEntry ->

            val bikeId = navBackStackEntry.arguments?.getString("bikeId")

            if (bikeId != null) {
                LaunchedEffect(key1 = bikeId,) {
                    bikePlaceViewModel.getSelectedBike(bikeId = bikeId)
                }
            }
            val selectedBike by bikePlaceViewModel.selectedBike.collectAsState()

            LaunchedEffect(key1 = selectedBike) {
                if (selectedBike != null || bikeId == "FIRST") {
                    Log.d("selectedBike", "${selectedBike?.bikeId}")
                    bikePlaceViewModel.updateBikeFields(selectedBike = selectedBike)
                }
            }
            BikeScreen(
                bikePlaceViewModel = bikePlaceViewModel,
                selectedBike = selectedBike,
                navController = navController,
                navigateToListScreen = { action: Action ->
                    navController.navigate("list/${action.name}") {
                        popUpTo(SearchScreen.Search.route)
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = SearchScreen.List.route,
            arguments = listOf(navArgument("action") {
                type = NavType.StringType
            })
        ) { navBackStackEntry: NavBackStackEntry ->

            val action = navBackStackEntry.arguments?.getString("action").toAction()

            LaunchedEffect(key1 = action) {
                bikePlaceViewModel.action.value = action
            }
            ListScreen(
                navigateToBikeScreen = { bikeId: String ->
                    navController.navigate("bikeEntry/$bikeId") {
                        popUpTo(SearchScreen.Search.route)
                        launchSingleTop = true
                    }
                },
                bikePlaceViewModel = bikePlaceViewModel,
                navController = navController
            )
        }
    }
}

sealed class SearchScreen(val route: String) {
    object Search: SearchScreen(route = "search")
    object BikeEntry: SearchScreen(route = "bikeEntry/{bikeId}")
    object List: SearchScreen(route = "list/{action}")
}