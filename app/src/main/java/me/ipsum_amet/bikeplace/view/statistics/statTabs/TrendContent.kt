package me.ipsum_amet.bikeplace.view.statistics.statTabs

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.jaikeerthick.composable_graphs.color.*
import com.jaikeerthick.composable_graphs.composables.LineGraph
import com.jaikeerthick.composable_graphs.data.GraphData
import com.jaikeerthick.composable_graphs.style.LineGraphStyle
import com.jaikeerthick.composable_graphs.style.LinearGraphVisibility
import me.ipsum_amet.bikeplace.util.M_PADDING
import me.ipsum_amet.bikeplace.util.S_PADDING

@Composable
fun TrendContent(
    day: String,
    amountMade: Int,
    lineGraphHeaderTitleSubValues: (Pair<Any, Any>) -> Unit
) {
    /*
    LineChart(
        lineChartData = LineChartData(
            points = listOf(
                LineChartData.Point(200f, "Sun"),
                LineChartData.Point(34f, "Mon"),
                LineChartData.Point(70f, "Tue"),
                LineChartData.Point(140f, "Wed"),
                LineChartData.Point(81f, "Thur"),
                LineChartData.Point(50f, "Fri"),
                LineChartData.Point(222f, "Sat")
            )
        ),
        // Optional properties.
        modifier = Modifier
            .padding(XL_PADDING)
            .fillMaxSize(0.90f),
        animation = simpleChartAnimation(),
        pointDrawer = FilledCircularPointDrawer(),
        lineDrawer = SolidLineDrawer(),
        xAxisDrawer = SimpleXAxisDrawer(),
        yAxisDrawer = SimpleYAxisDrawer(),
        horizontalOffset = 2f
    )

     */

    val style2 = LineGraphStyle(
        visibility = LinearGraphVisibility(
            isHeaderVisible = true,
            isYAxisLabelVisible = true,
            isCrossHairVisible = true
        ),
        colors = LinearGraphColors(
            lineColor = GraphAccent2,
            pointColor = GraphAccent2,
            clickHighlightColor = PointHighlight2,
            fillGradient = Brush.verticalGradient(
                listOf(Gradient3, Gradient2)
            )
        )
    )

    LineGraph(
        xAxisData = listOf("Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat").map {
            GraphData.String(it)
        },
        yAxisData = listOf(200, 34, 70, 140, 81, 50, 222),
        style = style2,
        header = {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Total Revenue of Week", fontWeight = FontWeight.ExtraLight)
                Spacer(modifier = Modifier.height(S_PADDING))
                Row(modifier = Modifier.padding(M_PADDING)) {
                    Text(
                        text = "Value: ",
                        fontWeight = FontWeight.ExtraLight,
                        modifier = Modifier.padding(end = S_PADDING)
                    )
                    Text(
                        text = "$day, $amountMade",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xff009E60)
                    )
                }
            }
        },
        onPointClicked = {
            lineGraphHeaderTitleSubValues(Pair(first = it.first, second = it.second))
        }
    )
} 