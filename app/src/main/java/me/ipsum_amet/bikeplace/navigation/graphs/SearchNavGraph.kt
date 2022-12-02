package me.ipsum_amet.bikeplace.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import me.ipsum_amet.bikeplace.view.confirmation.ConfirmationScreen
import me.ipsum_amet.bikeplace.view.summary.SummaryScreen
import me.ipsum_amet.bikeplace.view.locationDropOff.LocationDropOffScreen
import me.ipsum_amet.bikeplace.view.booking.BookingScreen
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
    composable(route = SearchScreen.LocationDropOff.route) {
        LocationDropOffScreen(
            navigateToPreviousScreen = {
                navController.navigateUp()
            },
            navigateToSummaryScreen = {
                navController.navigate(SearchScreen.Summary.route)
            },
            bikePlaceViewModel = bikePlaceViewModel
        )
    }
    composable(route = SearchScreen.Summary.route) {
        SummaryScreen(
            navigateToPreviousScreen = {
                navController.navigateUp()
        },
            bikePlaceViewModel = bikePlaceViewModel,
            navigateToBookingDetails = {
                navController.navigate(SearchScreen.BookingScreen.route)
            },
            navigateToConfirmationScreen = {
                navController.navigate(SearchScreen.ConfirmationScreen.route)

            },
            navigateToLocationDropOffScreen = {
                navController.navigate(SearchScreen.LocationDropOff.route)
            }
        )
    }
    composable(route = SearchScreen.ConfirmationScreen.route) {
        ConfirmationScreen(
            navigateToPreviousScreen = {
                navController.navigateUp()
            },
            navigateToBookingDetails = {
                navController.navigate(SearchScreen.BookingScreen.route)
            },
            navigateToHomeScreen = {
                navController.navigate(HomeScreen.Home.route)
            },
            bikePlaceViewModel = bikePlaceViewModel
        )
    }
    composable(route = HomeScreen.Booking.route) {
        BookingScreen(
            navigateToHomeScreen = {
                navController.navigate(HomeScreen.Booking.route)
            },
            navigateToPreviousScreen = {
              navController.navigateUp()
            },
            bikePlaceViewModel = bikePlaceViewModel,


        )
    }
}

sealed class SearchScreen(val route: String) {
    object Search: SearchScreen(route = "search")
    object LocationDropOff: SearchScreen(route = "locationDropOff")
    object Summary: SearchScreen(route = "summary")
    object BookingScreen: HomeScreen(route = "booking")
    object ConfirmationScreen: HomeScreen(route = "confirmation")
}