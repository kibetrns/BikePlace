package me.ipsum_amet.bikeplace.view.search

import androidx.compose.runtime.Composable
import me.ipsum_amet.bikeplace.util.RequestState
import me.ipsum_amet.bikeplace.util.SearchAppBarState
import me.ipsum_amet.bikeplace.components.Message
import me.ipsum_amet.bikeplace.components.ProgressBox
import me.ipsum_amet.bikeplace.data.model.Bike
import me.ipsum_amet.bikeplace.view.list.HandleListContent

@Composable
fun SearchContent(
    allBikes: RequestState<List<Bike>>,
    searchedBikes: RequestState<List<Bike>>,
    searchAppBarState: SearchAppBarState,
    navigateToBikeScreen: (bikeId: String) -> Unit
) {

    if (searchAppBarState == SearchAppBarState.TRIGGERED) {
        when (searchedBikes) {
            RequestState.Loading -> {
                ProgressBox()
            }
            RequestState.Idle -> {
                ProgressBox()
            }
            else -> {
                if (searchedBikes is RequestState.Success) {
                    HandleListContent(
                        bikes = searchedBikes.data,
                        navigateToBikeScreen = navigateToBikeScreen
                    )
                } else if (searchedBikes is RequestState.Error) {
                    Message(message = "No bikes with Inputted query.")
                }
            }
        }
    } else {
        when (allBikes) {
            RequestState.Loading -> {
                ProgressBox()
            }
            RequestState.Idle -> {
                ProgressBox()
            }
            else -> {
                if (allBikes is RequestState.Success) {
                    HandleListContent(
                        bikes = allBikes.data,
                        navigateToBikeScreen = navigateToBikeScreen
                    )
                } else if (allBikes is RequestState.Error) {
                    Message(message = "Error while displaying all Bikes")
                }
            }
        }
    }
}