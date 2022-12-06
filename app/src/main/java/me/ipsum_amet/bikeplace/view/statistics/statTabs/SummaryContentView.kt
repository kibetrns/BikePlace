package me.ipsum_amet.bikeplace.view.statistics.statTabs

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

@Composable
fun SummaryContentView(
    bikePlaceViewModel: BikePlaceViewModel,
    navigateToPreviousScreen: (Action) -> Unit,
) {

    /*

    LaunchedEffect(key1 = true) {
        bikePlaceViewModel.getAllCalculatedAmountsByCategoryInVM()
    }

     */


    val totalAccumulatedAmount by  bikePlaceViewModel.totalAccumulatedAmount.collectAsState()
    val totalAccumulatedAmountBMX by  bikePlaceViewModel.totalAccumulatedAmountBMX.collectAsState()
    val totalAccumulatedAmountCRUISER by  bikePlaceViewModel.totalAccumulatedAmountCRUISER.collectAsState()
    val totalAccumulatedAmountCYCLOCROSSBIKE by  bikePlaceViewModel.totalAccumulatedAmountCYCLOCROSSBIKE.collectAsState()
    val totalAccumulatedAmountELECTRICBIKE by  bikePlaceViewModel.totalAccumulatedAmountELECTRICBIKE.collectAsState()
    val totalAccumulatedAmountFOLDINGBIKE by  bikePlaceViewModel.totalAccumulatedAmountFOLDINGBIKE.collectAsState()
    val totalAccumulatedAmountHYBRIDBIKE by  bikePlaceViewModel.totalAccumulatedAmountHYBRIDBIKE.collectAsState()
    val totalAccumulatedAmountMOUNTAINBIKE by  bikePlaceViewModel.totalAccumulatedAmountMOUNTAINBIKE.collectAsState()
    val totalAccumulatedAmountRECUMBENTBIKE by  bikePlaceViewModel.totalAccumulatedAmountRECUMBENTBIKE.collectAsState()
    val totalAccumulatedAmountROADRIDE by  bikePlaceViewModel.totalAccumulatedAmountROADRIDE.collectAsState()
    val totalAccumulatedAmountTOURINGBIKE by  bikePlaceViewModel.totalAccumulatedAmountTOURINGBIKE.collectAsState()
    val totalAccumulatedAmountTRACKBIKE by  bikePlaceViewModel.totalAccumulatedAmountTRACKBIKE.collectAsState()

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
                            value = totalAccumulatedAmountBMX.toFloat(),
                            color = Color(0xffdb3a7a)
                        ),
                        BarChartData.Bar(
                            label = "CRU.",
                            value = totalAccumulatedAmountCRUISER.toFloat(),
                            color = Color(0xff676543)
                        ),
                        BarChartData.Bar(
                            label = "CYC.",
                            value = totalAccumulatedAmountCYCLOCROSSBIKE.toFloat(),
                            color = Color(0xffa2cc58)
                        ),
                        BarChartData.Bar(
                            label = "ELE.",
                            value = totalAccumulatedAmountELECTRICBIKE.toFloat(),
                            color = Color(0xffe40fcd)
                        ),
                        BarChartData.Bar(
                            label = "FOL.",
                            value = totalAccumulatedAmountFOLDINGBIKE.toFloat(),
                            color = Color(0xfff2fc65)
                        ),
                        BarChartData.Bar(
                            label = "HYB.",
                            value = totalAccumulatedAmountHYBRIDBIKE.toFloat(),
                            color = Color(0xff05ea4e)
                        ),
                        BarChartData.Bar(
                            label = "MOU.",
                            value = totalAccumulatedAmountMOUNTAINBIKE.toFloat(),
                            color = Color(0xff890313)
                        ),
                        BarChartData.Bar(
                            label = "REC.",
                            value = totalAccumulatedAmountRECUMBENTBIKE.toFloat(),
                            color = Color(0xffFFA500)
                        ),
                        BarChartData.Bar(
                            label = "ROA.",
                            value = totalAccumulatedAmountROADRIDE.toFloat(),
                            color = Color(0xff13240f)
                        ),
                        BarChartData.Bar(
                            label = "TOU.",
                            value = totalAccumulatedAmountTOURINGBIKE.toFloat(),
                            color = Color(0xff5e3e73)
                        ),
                        BarChartData.Bar(
                            label = "TRA.",
                            value = totalAccumulatedAmountTRACKBIKE.toFloat(),
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

