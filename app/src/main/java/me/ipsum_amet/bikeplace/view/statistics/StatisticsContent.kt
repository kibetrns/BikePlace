package me.ipsum_amet.bikeplace.view.statistics

import androidx.compose.foundation.layout.*
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.ipsum_amet.bikeplace.ui.theme.BikePlaceTheme
import me.ipsum_amet.bikeplace.util.L_PADDING
import me.ipsum_amet.bikeplace.util.M_PADDING
import me.ipsum_amet.bikeplace.util.S_PADDING



@Composable
fun StatisticsContent(
    tabTitles: List<String>,

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

            0 -> Text("Heello from 1")
            1 -> Text(" Heeloo from 2")
        }
    }

}


@Preview(showBackground = true, )
@Composable
fun PStatisticsContent() {
    BikePlaceTheme() {
        StatisticsContent(tabTitles = listOf("Summary", "weekly"))
        
    }
}