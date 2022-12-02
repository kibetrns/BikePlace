package me.ipsum_amet.bikeplace.view.summary

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import me.ipsum_amet.bikeplace.util.Action
import me.ipsum_amet.bikeplace.view.bikeDetails.PlainAppBar
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel


@Composable
fun SummaryScreen(
    navigateToPreviousScreen: (Action) -> Unit,
    bikePlaceViewModel: BikePlaceViewModel,
    navigateToBookingDetails: () -> Unit,
    navigateToConfirmationScreen: () -> Unit,
    navigateToLocationDropOffScreen: () -> Unit,
) {

    val subTotal =  bikePlaceViewModel.calculateTotalCheckoutPrice()

    val dropOffCost by remember { bikePlaceViewModel.dropOffCost }


    val VAT by remember { bikePlaceViewModel.VAT }


    val total = subTotal?.plus(dropOffCost)?.plus(VAT)

    val user = bikePlaceViewModel.user.value


    Scaffold(
        topBar = {
            PlainAppBar(
                title = "Summary",
                navigateToPreviousScreen = { action: Action ->
                    if (action == Action.NO_ACTION) {
                        navigateToPreviousScreen(action)
                    }
                }
            )
        },
        content = {
            if (user != null) {
                if (subTotal != null) {
                    if (total != null) {
                        SummaryContent(
                            subTotal = subTotal,
                            VAT = VAT,
                            dropOffCost = dropOffCost,
                            total = total,
                            user = user,
                            navigateToBookingDetails = { navigateToBookingDetails() },
                            navigateToConfirmationScreen = { navigateToConfirmationScreen() },
                            navigateToLocationDropOffScreen = { navigateToLocationDropOffScreen() },
                            makePayment = { bikePlaceViewModel.makeMpesaPayment() }
                        )
                    }
                }
            }
        }
    )
}