package me.ipsum_amet.bikeplace.view.home.adminTabs

import androidx.compose.runtime.Composable
import me.ipsum_amet.bikeplace.components.Message
import me.ipsum_amet.bikeplace.components.ProgressBox
import me.ipsum_amet.bikeplace.data.dto.response.BookingsInfoRes
import me.ipsum_amet.bikeplace.data.model.BookingInfo
import me.ipsum_amet.bikeplace.util.RequestState

@Composable
fun ReturnedContent(returnedBookingsInfo: RequestState<List<BookingsInfoRes>>) {
    when(returnedBookingsInfo) {
        RequestState.Loading -> {
            ProgressBox()
        }
        RequestState.Idle -> {
            ProgressBox()
        }
        else -> {
            if (returnedBookingsInfo is RequestState.Success) {
                HandleBookingContent(bookingsInfo = returnedBookingsInfo.data)
            } else if (returnedBookingsInfo is RequestState.Error) {
                Message(message = "Error While Displaying Returned Bookings Info. Records ")
            }
        }
    }
}