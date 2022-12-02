package me.ipsum_amet.bikeplace.view.booking

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDateTime
import me.ipsum_amet.bikeplace.R
import me.ipsum_amet.bikeplace.util.*
import me.ipsum_amet.bikeplace.components.Message
import me.ipsum_amet.bikeplace.components.ProgressBox
import me.ipsum_amet.bikeplace.data.dto.response.BookingsInfoRes
import me.ipsum_amet.bikeplace.data.model.BikeDropOffAddress
import me.ipsum_amet.bikeplace.data.model.BookingInfo
import me.ipsum_amet.bikeplace.data.model.ReturnStatus
import me.ipsum_amet.bikeplace.ui.theme.BikePlaceTheme

@Composable
fun BookingContent(bookingsInfo: RequestState<List<BookingInfo>>) {
    when(bookingsInfo) {
        RequestState.Loading -> {
            ProgressBox()
        }
        RequestState.Idle -> {
            ProgressBox()
        }
        else -> {
            if (bookingsInfo is RequestState.Success) {
                HandleBookingContent(bookingsInfo = bookingsInfo.data)
            } else if (bookingsInfo is RequestState.Error) {
                Message(message = "Error While Displaying Booking Info. ")
            }
        }
    }
}

@Composable
fun HandleBookingContent(
    bookingsInfo: List<BookingInfo>,
    ) {
    if (bookingsInfo.isEmpty()) {
        Message(message = "No records corresponding to userId")
    } else {
        DisplayBookingContent(bookingsInfo = bookingsInfo)
    }
}

@Composable
fun DisplayBookingContent(bookingsInfo: List<BookingInfo>) {
    LazyColumn(
        contentPadding = PaddingValues(3.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = bookingsInfo,
            key = { bookingsInfo: BookingInfo ->
                bookingsInfo::bookingId
            }
        ) { bookingsInfo: BookingInfo ->
            BookingItem(bookingsInfo = bookingsInfo)
        }
    }

    
}

data class DummyBooking(
    val BookingDate: String = "14th Jul 2020, 20:16",
    val dropOffLocation: String = "Ilkemi Triangle, Turkana",
    val BookingId: String = "QFK8ZQIEG4",
    val leaseActivation: String = "05 Aug 20, 10:00",
    val leaseExpiry: String = "08: Aug 20, 10:00",
    val userName:String = "Aquinas Dojo",
    val product:String = "Orange Hue",
    val price: String = "KES 333",
    val returnStatus: String = "Pending"
)

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
                    fontWeight = FontWeight.ExtraLight,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )
                bookingsInfo.bikeDropOffAddress?.let {
                    Text(
                        text = it.title,
                        fontWeight = FontWeight.Light,
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

/*
fun String.toFormatedDateTime(): String {

    val pattern = ""

    val pattern1 = "2022-11-23T19:22:00.000+00:00"

}

 */


@Preview(name = "BookingItem", showBackground = true, showSystemUi = true )
@Composable
fun PBookingCard() {
    val bookingsInfo = BookingInfo(
        bookingId = "QKN6JATBQQ",
        dateBookingMade = LocalDateTime(
            year = 2022,
            monthNumber = 11,
            dayOfMonth = 28,
            hour = 20,
            minute = 45,
        ),
        bikeDropOffAddress = me.ipsum_amet.bikeplace.data.dto.response.BikeDropOffAddress(
            area = "Ilkemi Trianlge",
            moreInfo = "dfdfdfdfdfe",
            streetName = "Q3-Avenue",
            title = "Home Office"
        ),
        bikeLeaseActivation = LocalDateTime(
            year = 2022,
            monthNumber = 11,
            dayOfMonth = 28,
            hour = 20,
            minute = 45,
        ),
        bikeLeaseExpiry = LocalDateTime(
            year = 2022,
            monthNumber = 11,
            dayOfMonth = 28,
            hour = 23,
            minute = 59,
        ),
        userName = "Aquinas",
        bikeName = "Leek Lake",
        amount = 1.0,
        bikeReturnStatus = ReturnStatus.PENDING,
    )

    BikePlaceTheme() {
        BookingItem(bookingsInfo)
    }
}

