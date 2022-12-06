package me.ipsum_amet.bikeplace.view.statistics.statTabs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import me.ipsum_amet.bikeplace.R
import me.ipsum_amet.bikeplace.util.*


@Composable
fun SummaryContent(

    leaseActivationTitle: String,
    leaseActivationDateInput: String,
    leaseActivationTimeInput: String,
    leaseExpiryTitle: String,
    leaseExpiryDateInput: String,
    leaseExpiryTimeInput: String,
    //totalAccumulatedAmount: Double,
    modifier: Modifier = Modifier,
    onLeaseActivationDateClicked: (String) -> Unit,
    onLeaseActivationTimeClicked: (String) -> Unit,
    onLeaseExpiryDateClicked: (String) -> Unit,
    onLeaseExpiryTimeClicked: (String) -> Unit,
    navigateToSummaryContentViewScreen: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(all = L_PADDING)
    ) {
        BookingGroup(
            leaseActivationTitle = leaseActivationTitle,
            leaseActivationDateInput = leaseActivationDateInput,
            leaseActivationTimeInput = leaseActivationTimeInput,
            leaseExpiryTitle = leaseExpiryTitle,
            leaseExpiryDateInput = leaseExpiryDateInput,
            leaseExpiryTimeInput = leaseExpiryTimeInput,
            onLeaseActivationDateClicked = onLeaseActivationDateClicked,
            onLeaseActivationTimeClicked = onLeaseActivationTimeClicked,
            onLeaseExpiryDateClicked = onLeaseExpiryDateClicked,
            onLeaseExpiryTimeClicked = onLeaseExpiryTimeClicked
        )
        Spacer(modifier = Modifier.height(L_PADDING))
        Button(
            modifier = Modifier
                .align(CenterHorizontally),
            onClick = { navigateToSummaryContentViewScreen() }
        ) {
            Text(
                text = stringResource(id = R.string.to_summary_content_view_button),
                letterSpacing = 1.sp,
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun IntervalEntry(
    timeInput: String,
    borderColor: Color,
    icon: ImageVector,
    onIntervalClicked: (String) -> Unit
) {
    Surface(
        border = BorderStroke(
            width = CHIP_WIDTH,
            color = borderColor
        ),
        onClick =  { onIntervalClicked(timeInput) },
        elevation = TIME_INTERVAL_ELEVATION,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(L_PADDING)
        ) {
            Text(text = timeInput)
            Spacer(modifier = Modifier.width(XL_PADDING))
            Icon(
                imageVector = icon,
                contentDescription = stringResource(id = R.string.calendar_icon)
            )
        }
    }
}


@Composable
fun IntervalSection(
    intervalTitle: String,
    dateInput: String,
    timeInput: String,
    cardBorderColor: Color,
    dateAndTimeBorderColor: Color,
    modifier: Modifier = Modifier,
    onLeaseDateClicked: (String) -> Unit,
    onLeaseTimeClicked: (String) -> Unit,
) {
    Card(
        border = BorderStroke(width = BIKE_CARD_WIDTH, color = cardBorderColor),
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(S_PADDING)
        ) {
            Text(
                text = intervalTitle,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Italic,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column() {
                    Text(text = "Date")
                    Spacer(modifier = Modifier.height(S_PADDING))
                    IntervalEntry(
                        timeInput = dateInput,
                        icon = Icons.Default.CalendarToday,
                        borderColor = dateAndTimeBorderColor,
                        onIntervalClicked =  onLeaseDateClicked ,
                    )
                }
                Column() {
                    Text(text = "Time")
                    Spacer(modifier = Modifier.height(S_PADDING))
                    IntervalEntry(
                        timeInput = timeInput,
                        icon = Icons.Default.Schedule,
                        borderColor = dateAndTimeBorderColor,
                        onIntervalClicked =  onLeaseTimeClicked
                    )
                }
            }
        }
    }
}

@Composable
fun BookingGroup(
    leaseActivationTitle: String,
    leaseActivationDateInput: String,
    leaseActivationTimeInput: String,
    leaseExpiryTitle: String,
    leaseExpiryDateInput: String,
    leaseExpiryTimeInput: String,
    modifier: Modifier = Modifier,
    onLeaseActivationDateClicked: (String) -> Unit,
    onLeaseActivationTimeClicked: (String) -> Unit,
    onLeaseExpiryDateClicked: (String) -> Unit,
    onLeaseExpiryTimeClicked: (String) -> Unit,
) {

    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(M_PADDING)
        ) {
            Text(
                text = "Choose Duration",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
            )
            IntervalSection(
                intervalTitle = leaseActivationTitle,
                dateInput = leaseActivationDateInput,
                timeInput = leaseActivationTimeInput,
                dateAndTimeBorderColor = Color.Green,
                cardBorderColor = MaterialTheme.colors.surface.copy(alpha = ContentAlpha.medium),
                modifier = Modifier
                    .padding(S_PADDING),
                onLeaseDateClicked = onLeaseActivationDateClicked,
                onLeaseTimeClicked = onLeaseActivationTimeClicked
            )
            /*
            Divider(
                color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
                thickness = CHIP_WIDTH,
                modifier = Modifier
                    .padding(top = S_PADDING, end = S_PADDING)
            )
             */
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CHIP_WIDTH)
                    .background(color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium))
            )
            IntervalSection(
                intervalTitle = leaseExpiryTitle,
                dateInput = leaseExpiryDateInput,
                timeInput = leaseExpiryTimeInput,
                cardBorderColor = MaterialTheme.colors.surface.copy(alpha = ContentAlpha.medium),
                dateAndTimeBorderColor = Color.Red,
                modifier = Modifier
                    .padding(S_PADDING),
                onLeaseDateClicked = onLeaseExpiryDateClicked,
                onLeaseTimeClicked = onLeaseExpiryTimeClicked
            )
        }
    }
}