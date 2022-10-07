package me.ipsum_amet.bikeplace.navigation.graphs

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.*
import androidx.navigation.compose.composable
import me.ipsum_amet.bikeplace.Util.Action
import me.ipsum_amet.bikeplace.view.bikeDetails.BikeDetailsScreen
import me.ipsum_amet.bikeplace.view.home.HomeScreen
import me.ipsum_amet.bikeplace.view.list.listByCategory.ListByCategoryScreen
import me.ipsum_amet.bikeplace.view.list.listByTopChoices.ListTopChoiceScreen
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

fun NavGraphBuilder.homeNavGraph(navController: NavHostController, bikePlaceViewModel: BikePlaceViewModel) {

    navigation(
        route = Graph.HOME,
        startDestination = HomeScreen.Home.route
    ) {
        composable(
            route = HomeScreen.Home.route,
        ) { navBackStackEntry: NavBackStackEntry ->

            LaunchedEffect(key1 = true) {
                bikePlaceViewModel.getTopChoiceBikes()
                bikePlaceViewModel.getAllBikeCategories()
            }

            HomeScreen(
                bikePlaceViewModel = bikePlaceViewModel,
                navController = navController,
                onCHomeBikeClicked = { bikeId: String ->
                    navController.navigate("categoryList/$bikeId") {
                        popUpTo(HomeScreen.Home.route)
                        launchSingleTop = true
                    }
                },
                navigateToTopChoiceBikes = {
                    navController.navigate(HomeScreen.TopChoiceList.route) {
                        popUpTo(HomeScreen.Home.route)
                        launchSingleTop = true
                    }
                },
                navigateToBikeDetailsScreen = { bikeId: String ->
                        navController.navigate("bikeDetails/$bikeId") {
                            popUpTo(HomeScreen.Home.route)
                            launchSingleTop = true
                        }
                }
            )
        }
    }

    composable(
        route = HomeScreen.BikeDetails.route,
        arguments = listOf(navArgument("bikeId") {
            type = NavType.StringType
        }
        )
    ) { navBackStackEntry ->

        val bikeId = navBackStackEntry.arguments?.getString("bikeId").toString()

        LaunchedEffect(key1 = bikeId) {
            bikePlaceViewModel.getSelectedBike(bikeId = bikeId)
        }

        val selectedBike by bikePlaceViewModel.selectedBike.collectAsState()

        BikeDetailsScreen(
            bikePlaceViewModel = bikePlaceViewModel,
            selectedBike = selectedBike,
            navigateToPreviousScreen = { action: Action ->
                navController.navigateUp()
                bikePlaceViewModel.action.value = action
            }
        )
    }

    composable(
        route = HomeScreen.CategoryList.route,
        arguments = listOf(navArgument("bikeId") {
            type = NavType.StringType
        }
        )
    ) { navBackStackEntry ->
        val bikeId = navBackStackEntry.arguments?.getString("bikeId").toString()

        val selectedBike by bikePlaceViewModel.selectedBike.collectAsState()

        LaunchedEffect(key1 = selectedBike) {
            bikePlaceViewModel.getSelectedBike(bikeId = bikeId)
            bikePlaceViewModel.getAllBikesByCategory()
        }

        val allBikesByCategory by bikePlaceViewModel.allBikesByCategory.collectAsState()

        ListByCategoryScreen(
            allBikesByCategory = allBikesByCategory,
            bikePlaceViewModel = bikePlaceViewModel,
            navigateToBikeDetailsScreen = { bikeId: String ->
                navController.navigate("bikeDetails/$bikeId") {
                    popUpTo(HomeScreen.Home.route)
                    launchSingleTop = true
                }
            },
            navigateToPreviousScreen = {
                navController.navigateUp()
            }
        )

    }

    composable(route = HomeScreen.TopChoiceList.route) {

        LaunchedEffect(key1 = true) {
            bikePlaceViewModel.getTopChoiceBikes()
        }

        val topChoiceBikes by bikePlaceViewModel.topBikeChoices.collectAsState()

        ListTopChoiceScreen(
            topChoicesBikes = topChoiceBikes,
            bikePlaceViewModel = bikePlaceViewModel,
            navigateToPreviousScreen = {
                navController.navigateUp()
            }
        ) { bikeId: String ->
            navController.navigate("bikeDetails/$bikeId") {
                popUpTo(HomeScreen.Home.route)
                launchSingleTop = true
            }
        }
    }
}

sealed class HomeScreen(val route: String) {
    object Home: HomeScreen(route = "home")
    object BikeDetails: HomeScreen(route = "bikeDetails/{bikeId}")
    object CategoryList: HomeScreen(route = "categoryList/{bikeId}")
    object TopChoiceList: HomeScreen(route = "topChoiceList")
}