package me.ipsum_amet.bikeplace.view.statistics.statTabs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
                            label = "Bar 1",
                            value = 832f,
                            color = Color.Green
                        ),
                        BarChartData.Bar(
                            label = "Bar 2",
                            value = 938f,
                            color = Color.Yellow
                        ),
                        BarChartData.Bar(
                            label = "Bar 3",
                            value = 535f,
                            color = Color.Cyan
                        ),
                        BarChartData.Bar(
                            label = "Bar 4",
                            value = 100f,
                            color = Color.Green
                        ),
                    )
                ),
                // Optional properties.

                modifier = Modifier
                    .fillMaxSize(0.90f)
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