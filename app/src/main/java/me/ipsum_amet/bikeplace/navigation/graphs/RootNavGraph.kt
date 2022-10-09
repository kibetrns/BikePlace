package me.ipsum_amet.bikeplace.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

@Composable
fun RootNavigationGraph(navController: NavHostController, bikePlaceViewModel: BikePlaceViewModel) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION
    ) {
        authNavGraph(navController = navController, bikePlaceViewModel = bikePlaceViewModel)

        homeAdminNavGraph(navController = navController, bikePlaceViewModel = bikePlaceViewModel)

        searchNavGraph(navController = navController, bikePlaceViewModel = bikePlaceViewModel)

        statNavGraph(navController = navController, bikePlaceViewModel = bikePlaceViewModel)

    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val SEARCH = "search_graph"
    const val STATISTICS = "statistics_graph"
}