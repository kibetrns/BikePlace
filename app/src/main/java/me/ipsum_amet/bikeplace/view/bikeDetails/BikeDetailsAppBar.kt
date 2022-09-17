package me.ipsum_amet.bikeplace.view.bikeDetails

import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import me.ipsum_amet.bikeplace.Util.Action
import me.ipsum_amet.bikeplace.view.bikeEntry.BackAction

@Composable
fun BikeDetailsAppBar(
    navigateToPreviousScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            BackAction(onBackClicked = navigateToPreviousScreen)
        },
        title = {},
        actions = {},
    )
}