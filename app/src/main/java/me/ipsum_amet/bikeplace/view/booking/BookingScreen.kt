package me.ipsum_amet.bikeplace.view.booking

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import me.ipsum_amet.bikeplace.data.dto.response.BookingsInfoRes
import me.ipsum_amet.bikeplace.view.bikeDetails.PlainAppBar
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

@Composable
fun BookingScreen(
    navigateToHomeScreen: () -> Unit,
    navigateToPreviousScreen: () -> Unit,
    bikePlaceViewModel: BikePlaceViewModel
) {

    LaunchedEffect(key1 = true) {
        bikePlaceViewModel.getAllBookingInfoOfUser()
    }

    val bookingInfo by bikePlaceViewModel.bookingsInfo.collectAsState()

    Scaffold(
        topBar = {
            PlainAppBar(
                title = "Booking Record(s)",
                navigateToPreviousScreen = { navigateToPreviousScreen() }
            )
        },
       content =  { BookingContent(bookingsInfo = bookingInfo) }
    )
}

val x = BookingsInfoRes
