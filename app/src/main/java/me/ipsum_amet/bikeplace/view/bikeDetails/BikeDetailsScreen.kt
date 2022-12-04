package me.ipsum_amet.bikeplace.view.bikeDetails

import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import me.ipsum_amet.bikeplace.util.Action
import me.ipsum_amet.bikeplace.data.model.Bike
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

@Composable
fun BikeDetailsScreen(
    bikePlaceViewModel: BikePlaceViewModel,
    selectedBike: Bike?,
    navigateToListScreen: (Action) -> Unit
) {
    val hoursToLease by remember { bikePlaceViewModel.hoursToLease }
    
    Scaffold(
        topBar = {
            BikeDetailsAppBar(
                navigateToListScreen = { action: Action ->
                    if ( action == Action.NO_ACTION ) {
                        navigateToListScreen(action)
                    }
                }
            )
        },
        content = {
            if (selectedBike != null) {
                BikeDetailsContent(
                    bike = selectedBike,
                    hoursToLease = hoursToLease,
                    totalPrice = bikePlaceViewModel.calculateTotalCheckoutPrice(),
                    onHoursToLeaseClicked = { bikePlaceViewModel.hoursToLease.value = it },
                )
            }
        }
    )
}

