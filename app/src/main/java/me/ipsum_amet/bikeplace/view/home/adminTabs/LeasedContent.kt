package me.ipsum_amet.bikeplace.view.home.adminTabs

import androidx.compose.runtime.Composable
import me.ipsum_amet.bikeplace.components.Message
import me.ipsum_amet.bikeplace.components.ProgressBox
import me.ipsum_amet.bikeplace.data.dto.response.BookingsInfoRes
import me.ipsum_amet.bikeplace.util.RequestState

@Composable
fun LeasedContent(
    leasedBookingsInfo: RequestState<List<BookingsInfoRes>>,
   // returnStatus: ReturnStatus,
    //onEditReturnStatusClicked: (ReturnStatus) -> Unit,
) {
    when(leasedBookingsInfo) {
        RequestState.Loading -> {
            ProgressBox()
        }
        RequestState.Idle -> {
            ProgressBox()
        }
        else -> {
            if (leasedBookingsInfo is RequestState.Success) {
                HandleBookingContent(bookingsInfo = leasedBookingsInfo.data)
            } else if (leasedBookingsInfo is RequestState.Error) {
                Message(message = "Error While Displaying Leased Bookings Info. Records ")
            }
        }
    }
}


