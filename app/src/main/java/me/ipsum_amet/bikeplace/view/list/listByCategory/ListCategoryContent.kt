package me.ipsum_amet.bikeplace.view.list.listByCategory

import androidx.compose.runtime.Composable
import me.ipsum_amet.bikeplace.Util.RequestState
import me.ipsum_amet.bikeplace.components.Message
import me.ipsum_amet.bikeplace.components.ProgressBox
import me.ipsum_amet.bikeplace.data.model.Bike
import me.ipsum_amet.bikeplace.view.list.HandleListContent

@Composable
fun ListCategoryContent(
    allBikesByCategory: RequestState<List<Bike>>,
    navigateToBikeDetailsScreen: (bikeId: String) -> Unit
) {
    when(allBikesByCategory) {
        RequestState.Loading -> {
            ProgressBox()
        }
        RequestState.Idle -> {
            ProgressBox()
        }
        else -> {
            if ( allBikesByCategory is RequestState.Error ) {
                Message(message = "Error While Listing Bikes By Top Choices")
            } else if (allBikesByCategory is RequestState.Success) {
                HandleListContent(
                    bikes = allBikesByCategory.data,
                    navigateToBikeScreen = navigateToBikeDetailsScreen
                )
            }
        }
    }


}