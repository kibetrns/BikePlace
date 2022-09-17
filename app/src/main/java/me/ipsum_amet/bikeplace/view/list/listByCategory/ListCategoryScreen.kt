package me.ipsum_amet.bikeplace.view.list.listByCategory

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import me.ipsum_amet.bikeplace.Util.Action
import me.ipsum_amet.bikeplace.Util.RequestState
import me.ipsum_amet.bikeplace.components.BottomNavBar
import me.ipsum_amet.bikeplace.data.model.Bike
import me.ipsum_amet.bikeplace.view.bikeDetails.BikeDetailsAppBar
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

@Composable
fun ListByCategoryScreen(
    allBikesByCategory: RequestState<List<Bike>>,
    bikePlaceViewModel: BikePlaceViewModel,
    navigateToPreviousScreen: (Action) -> Unit,
    navigateToBikeDetailsScreen: (bikeId: String) -> Unit
) {

    LaunchedEffect(key1 = true) {
        bikePlaceViewModel.getAllBikesByCategory()
    }

    Scaffold(
        topBar = {
            BikeDetailsAppBar(
                navigateToPreviousScreen = { action: Action ->
                    if (action == Action.NO_ACTION) {
                        navigateToPreviousScreen(action)
                    }
                }
            )
        },
    ) {
        ListCategoryContent(
            allBikesByCategory = allBikesByCategory,
            navigateToBikeDetailsScreen = navigateToBikeDetailsScreen
        )
    }


}