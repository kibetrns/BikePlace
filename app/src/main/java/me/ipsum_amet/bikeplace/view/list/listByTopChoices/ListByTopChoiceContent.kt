package me.ipsum_amet.bikeplace.view.list.listByTopChoices

import androidx.compose.runtime.Composable
import me.ipsum_amet.bikeplace.util.RequestState
import me.ipsum_amet.bikeplace.components.Message
import me.ipsum_amet.bikeplace.components.ProgressBox
import me.ipsum_amet.bikeplace.data.model.Bike
import me.ipsum_amet.bikeplace.view.list.HandleListContent

@Composable
fun ListByTopChoiceContent(
    topChoiceBikes: RequestState<List<Bike>>,
    navigateToBikeDetailsScreen: (bikeId: String) -> Unit
) {
    when(topChoiceBikes) {
        RequestState.Loading -> {
            ProgressBox()
        }
        RequestState.Idle -> {
            ProgressBox()
        }
        else -> {
            if ( topChoiceBikes is RequestState.Error ) {
                Message(message = "Error While Listing Bikes By Top Choices")
            } else if (topChoiceBikes is RequestState.Success) {
                HandleListContent(
                    bikes = topChoiceBikes.data,
                    navigateToBikeScreen = navigateToBikeDetailsScreen
                )
            }
        }
    }
}