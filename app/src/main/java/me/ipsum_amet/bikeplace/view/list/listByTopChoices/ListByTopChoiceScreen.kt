package me.ipsum_amet.bikeplace.view.list.listByTopChoices

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import me.ipsum_amet.bikeplace.util.Action
import me.ipsum_amet.bikeplace.util.RequestState
import me.ipsum_amet.bikeplace.data.model.Bike
import me.ipsum_amet.bikeplace.view.bikeDetails.PlainAppBar
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

@Composable
fun ListTopChoiceScreen(
    topChoicesBikes: RequestState<List<Bike>>,
    bikePlaceViewModel: BikePlaceViewModel,
    navigateToPreviousScreen: (Action) -> Unit,
    navigateToBikeDetailsScreen: (bikeId: String) -> Unit
) {

    LaunchedEffect(key1 = true,) {
        bikePlaceViewModel.getTopChoiceBikes()
    }

    Scaffold(
        topBar = {
            PlainAppBar(title = "") { action: Action ->
                if (action == Action.NO_ACTION) {
                    navigateToPreviousScreen(action)
                }
            }
        },
    ) {
        ListByTopChoiceContent(
            topChoiceBikes = topChoicesBikes,
            navigateToBikeDetailsScreen = navigateToBikeDetailsScreen
        )
    }
}

