package me.ipsum_amet.bikeplace.navigation.destinations

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import me.ipsum_amet.bikeplace.util.Action
import me.ipsum_amet.bikeplace.util.BIKE_ARGUMENT_KEY
import me.ipsum_amet.bikeplace.util.BIKE_DETAILS_SCREEN
import me.ipsum_amet.bikeplace.view.bikeDetails.BikeDetailsScreen
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

@RequiresApi(Build.VERSION_CODES.N)
fun NavGraphBuilder.bikeDetailsComposable(
    navigateToListScreen:(Action) -> Unit,
    bikePlaceViewModel: BikePlaceViewModel
) {
    composable(
        route = BIKE_DETAILS_SCREEN,
        arguments = listOf(navArgument(BIKE_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        val bikeId = navBackStackEntry.arguments?.getString(BIKE_ARGUMENT_KEY)

        if (bikeId != null) {
            LaunchedEffect(key1 = bikeId) {
                bikePlaceViewModel.getSelectedBike(bikeId = bikeId)
            }
        }
        val selectedBike by bikePlaceViewModel.selectedBike.collectAsState()

        LaunchedEffect(key1 = selectedBike) {
            if (selectedBike != null || bikeId == "FIRST") {
                Log.d("selectedBikeBDC", "${selectedBike?.bikeId}")
            }
        }
        BikeDetailsScreen(
            bikePlaceViewModel = bikePlaceViewModel,
            selectedBike = selectedBike,
            navigateToPreviousScreen = navigateToListScreen,
            navigateToSummaryScreen = {}
        )
    }
}

