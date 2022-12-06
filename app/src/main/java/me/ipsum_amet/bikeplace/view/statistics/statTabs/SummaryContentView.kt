package me.ipsum_amet.bikeplace.view.statistics.statTabs

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import me.bytebeats.views.charts.bar.BarChart
import me.bytebeats.views.charts.bar.BarChartData
import me.bytebeats.views.charts.bar.render.bar.SimpleBarDrawer
import me.bytebeats.views.charts.bar.render.label.SimpleLabelDrawer
import me.bytebeats.views.charts.bar.render.xaxis.SimpleXAxisDrawer
import me.bytebeats.views.charts.bar.render.yaxis.SimpleYAxisDrawer
import me.bytebeats.views.charts.simpleChartAnimation
import me.ipsum_amet.bikeplace.components.PlainAppBar
import me.ipsum_amet.bikeplace.util.Action
import me.ipsum_amet.bikeplace.util.L_PADDING

@Composable
fun SummaryContentView(
    navigateToPreviousScreen: (Action) -> Unit
) {



    //val totalAccumulatedAmount by  bikePlaceViewModel.totalAccumulatedAmount.collectAsState()

    Scaffold(
        topBar = {
            PlainAppBar(title = "", navigateToPreviousScreen = { action: Action ->
                if (action == Action.NO_ACTION) {
                    navigateToPreviousScreen(action)
                }
            }
            )
        },
        content = {
            BarChart(
                barChartData = BarChartData(
                    bars = listOf(
                        BarChartData.Bar(
                            label = "BMX",
                            value = 12f,
                            color = Color(0xffdb3a7a)
                        ),
                        BarChartData.Bar(
                            label = "CRU.",
                            value = 938f,
                            color = Color(0xff676543)
                        ),
                        BarChartData.Bar(
                            label = "CYC.",
                            value = 535f,
                            color = Color(0xffa2cc58)
                        ),
                        BarChartData.Bar(
                            label = "ELE.",
                            value = 100f,
                            color = Color(0xffe40fcd)
                        ),
                        BarChartData.Bar(
                            label = "FOL.",
                            value = 100f,
                            color = Color(0xfff2fc65)
                        ),
                        BarChartData.Bar(
                            label = "HYB.",
                            value = 100f,
                            color = Color(0xff05ea4e)
                        ),
                        BarChartData.Bar(
                            label = "MOU.",
                            value = 100f,
                            color = Color(0xff890313)
                        ),
                        BarChartData.Bar(
                            label = "REC.",
                            value = 100f,
                            color = Color(0xffFFA500)
                        ),
                        BarChartData.Bar(
                            label = "ROA.",
                            value = 100f,
                            color = Color(0xff13240f)
                        ),
                        BarChartData.Bar(
                            label = "TOU.",
                            value = 100f,
                            color = Color(0xff5e3e73)
                        ),
                        BarChartData.Bar(
                            label = "TRA.",
                            value = 100f,
                            color = Color(0xffeba5a1)
                        ),
                    )
                ),
                // Optional properties.

                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.80f)
                    .padding(L_PADDING),
                animation = simpleChartAnimation(),
                barDrawer = SimpleBarDrawer(),
                xAxisDrawer = SimpleXAxisDrawer(),
                yAxisDrawer = SimpleYAxisDrawer(),
                labelDrawer = SimpleLabelDrawer(drawLocation = SimpleLabelDrawer.DrawLocation.XAxis)
            )
        }
    )
}

