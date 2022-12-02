package me.ipsum_amet.bikeplace.view.locationDropOff

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.firestore.GeoPoint
import me.ipsum_amet.bikeplace.data.model.User
import me.ipsum_amet.bikeplace.ui.theme.BikePlaceTheme
import me.ipsum_amet.bikeplace.R
import me.ipsum_amet.bikeplace.util.*
import me.ipsum_amet.bikeplace.data.model.BikeDropOffAddress

@Composable
fun AddressCard(
    user: User,
    title: String,
    area: String,
    streetName: String,
    moreInfo: String,
    geoPoint: GeoPoint?,
    navigateToEditAddressPopUp: () -> Unit,
) {
    Card(
        border = BorderStroke(width = BIKE_CARD_WIDTH, color = Color(0xFF2B3230)),
        backgroundColor = Color(0xFFf8f8ff),
        modifier = Modifier
            .wrapContentSize(unbounded = false)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(L_PADDING, L_PADDING)
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box() {
                    Icon(
                        painter = painterResource(id = R.drawable.foundation),
                        contentDescription = stringResource(
                            id = R.string.home_work_icon
                        ),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(end = XXXL_PADDING)
                    )
                    Text(
                        text = title,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                    )
                }
                IconButton(onClick = {
                    navigateToEditAddressPopUp()
                }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(id = R.string.edit_icon)
                    )
                }
            }
            Text(
                text = area,
                softWrap = true,
                overflow = TextOverflow.Ellipsis

            )
            Spacer(modifier = Modifier.height(M_PADDING))
            Text(text = streetName)

            Divider(
                thickness = BIKE_CARD_WIDTH,
                color = Color(0xFF353935),
                modifier = Modifier
                    .padding(vertical = M_PADDING)
            )
            Text(text = moreInfo)
        }
    }
}

@Preview("AddressCard", showBackground = true, showSystemUi = true)
@Composable
fun PAddressCard() {
    val user = User(
        dropOffLocation = BikeDropOffAddress(
            title = "Office",
            area = "Nyeri",
            streetName = "#532943",
            moreInfo = "Room #1 Ground Floor, FireStone Building Block A dfdferwrwqererewrqwerqrerqerqrererwqwerqerrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
        )

    )
        val title = "dfdfkd"
        val area = "dfdfd"
        val streetName = "dfjdkfs"
        val moreInfo = "dfdklfdkfdklfdl"

    BikePlaceTheme(darkTheme = false) {
        AddressCard(
            user = user,
            title = title,
            area = area,
            streetName = streetName,
            moreInfo = moreInfo,
            geoPoint = null,
            navigateToEditAddressPopUp = {}
        )


    }


}