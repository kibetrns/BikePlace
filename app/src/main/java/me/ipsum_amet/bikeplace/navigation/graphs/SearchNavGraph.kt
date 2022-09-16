package me.ipsum_amet.bikeplace.navigation.graphs

import androidx.compose.material.Scaffold
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import me.ipsum_amet.bikeplace.components.BottomNavBar
import me.ipsum_amet.bikeplace.components.Message
import me.ipsum_amet.bikeplace.view.search.SearchScreen
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

fun NavGraphBuilder.searchNavGraph(navController: NavHostController, bikePlaceViewModel: BikePlaceViewModel) {

    navigation(
        route = Graph.SEARCH,
        startDestination = SearchScreen.Search.route
    ) {
        composable(
            route = SearchScreen.Search.route
        ) {
            SearchScreen(
                navigateToBikeDetailsScreen = { bikeId: String ->
                    navController.navigate("bikeDetails/$bikeId") {
                        popUpTo(SearchScreen.Search.route)
                        launchSingleTop = true
                    }

                },
                navController = navController,
                bikePlaceViewModel = bikePlaceViewModel
            )
        }
    }
}

sealed class SearchScreen(val route: String) {
    object Search: SearchScreen(route = "search")
}