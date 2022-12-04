package me.ipsum_amet.bikeplace.view.home.adminTabs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RequestQuote
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import me.ipsum_amet.bikeplace.R
import me.ipsum_amet.bikeplace.util.*
import me.ipsum_amet.bikeplace.components.Message
import me.ipsum_amet.bikeplace.components.ProgressBox
import me.ipsum_amet.bikeplace.data.dto.response.BookingsInfoRes
import me.ipsum_amet.bikeplace.data.dto.response.toBookingInfo
import me.ipsum_amet.bikeplace.data.model.BookingInfo

@Composable
fun AllContent(
    allBookingsInfo: RequestState<List<BookingsInfoRes>>,
    searchedBookingInfo: RequestState<BookingsInfoRes?>,
    searchAppBarState: SearchAppBarState

) {

    if (searchAppBarState == SearchAppBarState.TRIGGERED) {

        when(searchedBookingInfo) {
            RequestState.Loading -> {
                ProgressBox()
            }
            RequestState.Idle -> {
                ProgressBox()
            }
            else -> {
                if (searchedBookingInfo is RequestState.Success) {
                    searchedBookingInfo.data?.let { BookingItem(bookingsInfo = it.toBookingInfo()) }
                } else if(searchedBookingInfo is RequestState.Error) {
                    Message(
                        message = "Error while searching... " +
                                "Check net. connection or input valid receipt no."
                    )
                }
            }
        }
        /*

        if (searchedBookingInfo is RequestState.Success) {
            BookingItem(bookingsInfo = searchedBookingInfo.data)
        } else if (searchedBookingInfo is RequestState.Loading) {
            ProgressBox()
        } else if (searchedBookingInfo is RequestState.Error) {
            Message(message = "Error while searching... Check net. connection or input valid receipt no.")
        }

         */
        
    } else {

        when (allBookingsInfo) {
            RequestState.Loading -> {
                ProgressBox()
            }
            RequestState.Idle -> {
                ProgressBox()
            }
            else -> {
                if (allBookingsInfo is RequestState.Success) {
                    HandleBookingContent(bookingsInfo = allBookingsInfo.data)
                } else if (allBookingsInfo is RequestState.Error) {
                    Message(message = "Error While Displaying All Bookings Info. Records")
                }
            }
        }
    }

}


@Composable
fun HandleBookingContent(
    bookingsInfo: List<BookingsInfoRes>,
) {
    if (bookingsInfo.isEmpty()) {
        Message(message = "No records...")
    } else {
        DisplayBookingContent(bookingsInfo = bookingsInfo)
    }
}

@Composable
fun DisplayBookingContent(bookingsInfo: List<BookingsInfoRes>) {
    LazyColumn(
        contentPadding = PaddingValues(3.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(
            items = bookingsInfo,
            key = { bookingsInfo: BookingsInfoRes ->
                bookingsInfo::bookingId
            }
        ) { bookingsInfo: BookingsInfoRes ->
            BookingItem(bookingsInfo = bookingsInfo.toBookingInfo()
            )
        }
    }
}

@Composable
fun BookingItem(bookingsInfo: BookingInfo) {
    Card(
        border = BorderStroke(width = BIKE_CARD_WIDTH, color = Color.Gray),
        shape = RoundedCornerShape(L_PADDING),
        modifier = Modifier
            .wrapContentSize()
            .padding(bottom = L_PADDING)
    ) {
        Column(Modifier
            .padding(M_PADDING)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(M_PADDING),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = bookingsInfo.dateBookingMade.toString(),
                    //formatISODate(bookingsInfo.dateBookingMade),
                    fontWeight = FontWeight.W100,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )
                bookingsInfo.bikeDropOffAddress?.let {
                    Text(
                        text = it.title,
                        fontWeight = FontWeight.W100,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Divider(color = LightGray, thickness = BIKE_CARD_WIDTH)
            Row(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(vertical = S_PADDING),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.RequestQuote,
                            contentDescription = stringResource(
                                id = R.string.bookingId_icon
                            ),
                            modifier = Modifier
                                .padding(end = S_PADDING)
                        )
                        Text(text = bookingsInfo.bookingId, fontWeight = FontWeight.ExtraBold)
                    }
                    Text(text = "${bookingsInfo.bikeLeaseActivation} -> ${bookingsInfo.bikeLeaseExpiry} "

                        //"${formatISODate(bookingsInfo.bikeLeaseActivation)} -> ${formatISODate(bookingsInfo.bikeLeaseExpiry)}"
                    )
                }

                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(54f)),
                    color = LightGray
                ) {
                    Text(
                        text = "Booked",
                        modifier = Modifier
                            .padding(S_PADDING),
                        maxLines = 1,
                        color = Green,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Surface(
                modifier = Modifier
                    .padding(vertical = S_PADDING),
                shape = RoundedCornerShape(7.dp),
                color = LightGray
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(start = S_PADDING, end = S_PADDING),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Customer",
                        fontWeight = FontWeight.ExtraLight,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = bookingsInfo.userName,
                        fontWeight = FontWeight.ExtraBold,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Surface(
                modifier = Modifier
                    .padding(vertical = S_PADDING),
                shape = RoundedCornerShape(7.dp),
                color = Transparent
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(start = S_PADDING, end = S_PADDING),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Product",
                        fontWeight = FontWeight.ExtraLight,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = bookingsInfo.bikeName,
                        fontWeight = FontWeight.ExtraBold,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Surface(
                modifier = Modifier
                    .padding(vertical = S_PADDING),
                shape = RoundedCornerShape(7.dp),
                color = LightGray
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(start = S_PADDING, end = S_PADDING),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Price",
                        fontWeight = FontWeight.ExtraLight,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = "${bookingsInfo.amount}",
                        fontWeight = FontWeight.ExtraBold,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Surface(
                modifier = Modifier
                    .padding(vertical = S_PADDING),
                shape = RoundedCornerShape(7.dp),
                color = Transparent
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(start = S_PADDING, end = S_PADDING),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Return Status",
                        fontWeight = FontWeight.ExtraLight,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = bookingsInfo.bikeReturnStatus.name,
                        fontWeight = FontWeight.ExtraBold,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}