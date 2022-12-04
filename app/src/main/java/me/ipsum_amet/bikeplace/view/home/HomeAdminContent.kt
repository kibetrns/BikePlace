package me.ipsum_amet.bikeplace.view.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.ipsum_amet.bikeplace.data.dto.response.BookingsInfoRes
import me.ipsum_amet.bikeplace.data.model.BookingInfo
import me.ipsum_amet.bikeplace.util.M_PADDING
import me.ipsum_amet.bikeplace.util.RequestState
import me.ipsum_amet.bikeplace.util.SearchAppBarState
import me.ipsum_amet.bikeplace.view.home.adminTabs.AllContent
import me.ipsum_amet.bikeplace.view.home.adminTabs.LeasedContent
import me.ipsum_amet.bikeplace.view.home.adminTabs.ReturnedContent


@Composable
fun HomeAdminContent(
    searchedBookingInfo: RequestState<BookingsInfoRes?>,
    homeAdminSearchAppBarState: SearchAppBarState,
    bookingsInfo: RequestState<List<BookingsInfoRes>>,
    leasedBookingsInfo: RequestState<List<BookingsInfoRes>>,
    returnedBookingsInfo: RequestState<List<BookingsInfoRes>>,
    tabTitles: List<String>,
    onEditReturnStatusClicked:() -> Unit
) {
    var tabIndex by remember { mutableStateOf(0) } // 1.
    Column { // 2.

        Spacer(modifier = Modifier.height(M_PADDING))
        TabRow(selectedTabIndex = tabIndex) { // 3.
            tabTitles.forEachIndexed { index, title ->
                Tab(selected = tabIndex == index, // 4.
                    onClick = { tabIndex = index },
                    text = { Text(text = title) }) // 5.
            }
        }
        when (tabIndex) { // 6.
            0 -> AllContent(
                allBookingsInfo = bookingsInfo,
                searchedBookingInfo = searchedBookingInfo,
                searchAppBarState = homeAdminSearchAppBarState
            )
            1 -> LeasedContent(leasedBookingsInfo = leasedBookingsInfo)
            2 -> ReturnedContent(returnedBookingsInfo = returnedBookingsInfo)
        }
    }
}

@Preview
@Composable
fun PHomeAdminContent() {

}
