package me.ipsum_amet.bikeplace.view.confirmation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Arrangement.SpaceEvenly
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import me.ipsum_amet.bikeplace.R
import me.ipsum_amet.bikeplace.util.BIKE_CARD_ELEVATION
import me.ipsum_amet.bikeplace.util.L_PADDING
import me.ipsum_amet.bikeplace.util.S_PADDING
import me.ipsum_amet.bikeplace.data.model.User

@Composable
fun ConfirmationContent(
    user: User,
    orderId: String,
    navigateToBookingDetails: () -> Unit,
    navigateToHomeScreen: () -> Unit,
) {
    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = SpaceBetween,
    ) {
        Card(
            elevation = BIKE_CARD_ELEVATION,
            shape = RoundedCornerShape(7),
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = SpaceEvenly
            ) {
                Image(
                    painter = painterResource(id = R.drawable.confirmation_success),
                    contentDescription = stringResource(
                        id = R.string.success_confirmation
                    ),
                    modifier = Modifier
                        .fillMaxHeight(0.34f)
                )
                Text("Hey ${user.fullName?.take(7)}.... ,")
                Text("Thanks for your Booking ")
                TextButton(onClick = { navigateToBookingDetails() },) {
                    Text(text = " See Booking Details ->")
                }
            }
        }
        Spacer(modifier = Modifier.height(L_PADDING))
        Button(
            onClick = { navigateToHomeScreen() },
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(S_PADDING)
        ) {
            Text(text = "Continue Bike Browsing")
        }
    }
}
