package me.ipsum_amet.bikeplace.view.list.listByTopChoices

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import me.ipsum_amet.bikeplace.Util.RequestState
import me.ipsum_amet.bikeplace.data.model.Bike
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

@Composable
fun ListTopChoiceScreen(
    topChoicesBikes: RequestState<List<Bike>>,
    bikePlaceViewModel: BikePlaceViewModel,
    navigateToBikeDetailsScreen: (bikeId: String) -> Unit
) {

    LaunchedEffect(key1 = true,) {
        bikePlaceViewModel.getTopChoiceBikes()
    }

    ListByTopChoiceContent(
        topChoiceBikes = topChoicesBikes,
        navigateToBikeDetailsScreen = navigateToBikeDetailsScreen
    )
}