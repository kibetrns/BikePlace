package me.ipsum_amet.bikeplace.navigation.graphs

import androidx.compose.material.Scaffold
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import me.ipsum_amet.bikeplace.components.BottomNavBar
import me.ipsum_amet.bikeplace.components.Message
import me.ipsum_amet.bikeplace.view.home.HomeAdminScreen
import me.ipsum_amet.bikeplace.view.home.HomeAppBar
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

fun NavGraphBuilder.homeAdminNavGraph(
    navController: NavHostController,
    bikePlaceViewModel: BikePlaceViewModel
) {
    navigation(
        route = Graph.HOME,
        startDestination = HomeAdminScreen.Home.route
    ) {
        composable(route = HomeAdminScreen.Home.route) {
            HomeAdminScreen(
                bikePlaceViewModel = bikePlaceViewModel,
                navController = navController,
            )
        }
    }
}

sealed class HomeAdminScreen(val route: String) {
    object Home: HomeAdminScreen(route = "home")

    object Dummy: HomeAdminScreen(route = "dummy")

}