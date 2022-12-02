package me.ipsum_amet.bikeplace.view.summary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.outlined.Paid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import me.ipsum_amet.bikeplace.R
import me.ipsum_amet.bikeplace.data.model.User
import me.ipsum_amet.bikeplace.util.*

@Composable
fun SummaryContent(
    subTotal: Double,
    VAT: Double,
    dropOffCost: Double,
    total: Double,
    user: User,
    navigateToBookingDetails: () -> Unit,
    navigateToConfirmationScreen: () -> Unit,
    navigateToLocationDropOffScreen: () -> Unit,
    makePayment: () -> Unit
) {
    Column {
        Card(
            elevation = BIKE_CARD_WIDTH,
            shape = RoundedCornerShape(7),
            modifier = Modifier
                .fillMaxHeight(0.33f)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(S_PADDING)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Sub Total",
                        fontSize = MaterialTheme.typography.subtitle1.fontSize,
                        fontWeight = FontWeight.ExtraLight,
                    )
                    Text(text = "KES $subTotal", fontWeight = FontWeight.Bold)
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "VAT ($VAT_CONST%)",
                        fontSize = MaterialTheme.typography.subtitle1.fontSize,
                        fontWeight = FontWeight.ExtraLight
                    )
                    Text(text = "KES $VAT", fontWeight = FontWeight.Bold)
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "DropOff Charge",
                        fontSize = MaterialTheme.typography.subtitle1.fontSize,
                        fontWeight = FontWeight.ExtraLight
                    )
                    Text(text = "KES $dropOffCost", fontWeight = FontWeight.Bold)
                }
                //Spacer(modifier = Modifier.height(M_PADDING))
                Divider(thickness = BIKE_CARD_WIDTH, color = Color.LightGray)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Total",
                        fontSize = MaterialTheme.typography.subtitle1.fontSize,
                        fontWeight = FontWeight.ExtraLight
                    )
                    Text(
                        text = "KES $total",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFff3800)
                    )
                }
               // Spacer(modifier = Modifier.height(M_PADDING))
            }
        }
        Spacer(modifier = Modifier.height(M_PADDING))
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Payment Method",
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    modifier = Modifier.padding(end = S_PADDING)
                )
                Icon(
                    imageVector = Icons.Outlined.Paid,
                    contentDescription = stringResource(id = R.string.credit_card_icon)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.m_pesa_01),
                    contentDescription = stringResource(
                        id = R.string.mpesa_africa_icon
                    ),
                    modifier = Modifier.padding(end = L_PADDING)
                )
                user.phoneNumber?.let { Text(text = it) }
            }
            TextButton(
                modifier = Modifier.align(CenterHorizontally),
                onClick = { navigateToLocationDropOffScreen() }
            ) {
                Text(text = "Input Drop Off Location ?")
            }
            Spacer(modifier = Modifier.height(M_PADDING))
            Button(
                onClick = { makePayment() },
                modifier = Modifier.align(CenterHorizontally)
            ) {
                Text(text = "Place Payment")
            }
            Button(onClick = { navigateToConfirmationScreen() }) {
                Text(text = "DUmmy Button (testing for navigation)")
            }
        }
    }
}