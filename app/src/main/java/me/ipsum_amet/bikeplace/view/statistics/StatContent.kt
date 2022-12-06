package me.ipsum_amet.bikeplace.view.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.ipsum_amet.bikeplace.ui.theme.BikePlaceTheme
import me.ipsum_amet.bikeplace.util.M_PADDING
import me.ipsum_amet.bikeplace.view.statistics.statTabs.SummaryContent
import me.ipsum_amet.bikeplace.view.statistics.statTabs.TrendContent


@Composable
fun StatisticsContent(
    leaseActivationTitle: String,
    leaseActivationDateInput: String,
    leaseActivationTimeInput: String,
    leaseExpiryTitle: String,
    leaseExpiryDateInput: String,
    leaseExpiryTimeInput: String,
    modifier: Modifier = Modifier,
    //totalAccumulatedAmount: Double,
    day: String,
    amountMade: Int,
    onLeaseActivationDateClicked: (String) -> Unit,
    onLeaseActivationTimeClicked: (String) -> Unit,
    onLeaseExpiryDateClicked: (String) -> Unit,
    onLeaseExpiryTimeClicked: (String) -> Unit,
    navigateToSummaryContentViewScreen: () -> Unit,
    lineGraphHeaderTitleSubValues: (Pair<Any, Any>) -> Unit
) {
    val tabTitles = listOf("Trends", "Summary")

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

            0 -> TrendContent(
                day = day,
                amountMade = amountMade,
                lineGraphHeaderTitleSubValues = lineGraphHeaderTitleSubValues
            )

            1 -> SummaryContent(
                leaseActivationTitle = leaseActivationTitle,
                leaseActivationDateInput =  leaseActivationDateInput,
                leaseActivationTimeInput = leaseActivationTimeInput,
                leaseExpiryTitle = leaseExpiryTitle,
                leaseExpiryDateInput = leaseExpiryDateInput,
                leaseExpiryTimeInput = leaseExpiryTimeInput,
                modifier = Modifier,
                onLeaseActivationDateClicked = onLeaseActivationDateClicked,
                onLeaseActivationTimeClicked = onLeaseActivationTimeClicked,
                onLeaseExpiryDateClicked = onLeaseExpiryDateClicked,
                onLeaseExpiryTimeClicked = onLeaseExpiryTimeClicked,
                navigateToSummaryContentViewScreen = navigateToSummaryContentViewScreen
            )
        }
    }

}






@Preview(showBackground = true, )
@Composable
fun PStatisticsContent() {
    BikePlaceTheme() {
        SummaryContent(
            leaseActivationTitle = "Pick Start Range",
            leaseActivationDateInput =  "05/12/2022",
            leaseActivationTimeInput = "10:29",
            leaseExpiryTitle = "Pick End Range",
            leaseExpiryDateInput = "10/12/2022",
            leaseExpiryTimeInput = "23:23",
            modifier = Modifier,
            onLeaseActivationDateClicked = {},
            onLeaseActivationTimeClicked = {},
            onLeaseExpiryDateClicked = {},
            onLeaseExpiryTimeClicked = {}
        ) {}
    }
}