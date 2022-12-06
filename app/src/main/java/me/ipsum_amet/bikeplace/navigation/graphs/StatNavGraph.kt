package me.ipsum_amet.bikeplace.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import me.ipsum_amet.bikeplace.view.statistics.StatScreen
import me.ipsum_amet.bikeplace.view.statistics.statTabs.SummaryContentView
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
            StatScreen(
                navController = navController,
                bikePlaceViewModel = bikePlaceViewModel,
                navigateToSummaryContentViewScreen = {
                    navController.navigate(StatisticsScreen.SummaryContentView.route) {
                        popUpTo(StatisticsScreen.Statistics.route)
                        launchSingleTop = true
                    }

                }
            )
        }
        composable(route = StatisticsScreen.SummaryContentView.route) {
            SummaryContentView { navController.navigateUp() }
        }
    }

}
sealed class StatisticsScreen(val route: String) {
    object Statistics: StatisticsScreen(route = "statistics")

    object SummaryContentView: StatisticsScreen(route = "summaryContentView")

    object TrendingContentView: StatisticsScreen(route = "trendingContentView")
}