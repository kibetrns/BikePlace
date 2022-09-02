package me.ipsum_amet.bikeplace.navigation.destinations

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import me.ipsum_amet.bikeplace.Util.Action
import me.ipsum_amet.bikeplace.Util.BIKE_ARGUMENT_KEY
import me.ipsum_amet.bikeplace.Util.BIKE_SCREEN
import me.ipsum_amet.bikeplace.view.bike.BikeScreen
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

fun NavGraphBuilder.bikeComposable(
    navigateToListScreen: (Action) -> Unit,
    bikePlaceViewModel: BikePlaceViewModel
) {
    composable(
        route = BIKE_SCREEN,
        arguments = listOf(navArgument(BIKE_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        val bikeId = navBackStackEntry.arguments?.getString(BIKE_ARGUMENT_KEY)

        if (bikeId != null) {
            bikePlaceViewModel.getSelectedBike(bikeId = bikeId)
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
                navigateToListScreen = navigateToListScreen
            )

    }
}