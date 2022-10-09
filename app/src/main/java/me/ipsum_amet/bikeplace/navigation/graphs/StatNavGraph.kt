package me.ipsum_amet.bikeplace.navigation.graphs

import androidx.compose.material.Scaffold
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import me.ipsum_amet.bikeplace.components.BottomNavBar
import me.ipsum_amet.bikeplace.components.Message
import me.ipsum_amet.bikeplace.view.home.HomeAppBar
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

fun NavGraphBuilder.statNavGraph(
    navController: NavHostController,
    bikePlaceViewModel: BikePlaceViewModel
) {
    navigation(
        route = Graph.STATISTICS,
        startDestination = StatisticsScreen.Statistics.route
    ) {
        composable(route = StatisticsScreen.Statistics.route) {
            Scaffold(
                topBar = { HomeAppBar() },
                bottomBar = { BottomNavBar(navController = navController) }
            ) {
                Message(message = "Statistics Screen")
            }
        }
    }

}
sealed class StatisticsScreen(val route: String) {
    object Statistics: StatisticsScreen(route = "statistics")
}