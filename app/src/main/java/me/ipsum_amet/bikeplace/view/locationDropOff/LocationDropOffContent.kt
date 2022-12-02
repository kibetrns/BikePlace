package me.ipsum_amet.bikeplace.view.locationDropOff

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.google.firebase.firestore.GeoPoint
import me.ipsum_amet.bikeplace.util.M_PADDING
import me.ipsum_amet.bikeplace.util.S_PADDING
import me.ipsum_amet.bikeplace.data.model.User
import me.ipsum_amet.bikeplace.R
import me.ipsum_amet.bikeplace.util.L_PADDING

@Composable
fun LocationDropOffContent(
    user: User,
    title: String,
    area: String,
    streetName: String,
    moreInfo: String,
    geoPoint: GeoPoint?,
    navigateToEditAddressPopUp: (Boolean) -> Unit,
    navigateToSummaryScreen: () -> Unit
) {
    Column(modifier = Modifier.wrapContentSize()) {
        AddressCard(
            user = user,
            title = title,
            area = area,
            streetName = streetName,
            moreInfo = moreInfo,
            geoPoint = geoPoint,
            navigateToEditAddressPopUp = {
                navigateToEditAddressPopUp(true)
            }
        )
        Spacer(modifier = Modifier.height(L_PADDING))
        Text(text = "Personal Info", fontWeight = FontWeight.Bold)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = S_PADDING)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.foundation),
                contentDescription = stringResource(
                    id = R.string.address_icon
                ),
                tint = Color.LightGray,
                modifier = Modifier
                    .padding(end = M_PADDING)
            )
            Text(
                text = "Drop Off to $title address",
                fontWeight = FontWeight.Light
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = S_PADDING)
        ) {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = stringResource(
                    id = R.string.address_icon
                ),
                tint = Color.LightGray,
                modifier = Modifier
                    .padding(end = M_PADDING)
            )
            Text(
                text = "${user.phoneNumber}",
                fontWeight = FontWeight.Light
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = S_PADDING)
        ) {
            Icon(
                painterResource(id = R.drawable.email),
                contentDescription = stringResource(
                    id = R.string.email_icon
                ),
                tint = Color.LightGray,
                modifier = Modifier
                    .padding(end = M_PADDING)
            )
            Text(
                text = "******@**.***",
                fontWeight = FontWeight.Light
            )
        }
        Spacer(modifier = Modifier.height(L_PADDING))
        Button(
            modifier = Modifier
                .align(CenterHorizontally),
            onClick = { navigateToSummaryScreen() }
        ) {
            Text(text = "Continue")
        }
    }
}

@Composable
fun PersonalInfoSubSection(
    content: @Composable ColumnScope.() -> Unit
) {

}

