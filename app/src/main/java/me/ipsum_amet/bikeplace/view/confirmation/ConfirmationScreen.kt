package me.ipsum_amet.bikeplace.view.confirmation

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import me.ipsum_amet.bikeplace.view.bikeDetails.PlainAppBar
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

@Composable
fun ConfirmationScreen(
    navigateToPreviousScreen: () -> Unit,
    navigateToBookingDetails: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    bikePlaceViewModel: BikePlaceViewModel
) {

    val user = bikePlaceViewModel.user.value
    val orderId = "#Q5NOW29V"
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Confirmation")})
    },
        content = {
            if (user != null) {
                ConfirmationContent(
                    user = user,
                    orderId = orderId,
                    navigateToBookingDetails = { navigateToBookingDetails() },
                    navigateToHomeScreen = {
                        navigateToHomeScreen()
                    }
                )
            }
        }
    )
}