package me.ipsum_amet.bikeplace.view.bikeDetails

import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import me.ipsum_amet.bikeplace.util.Action
import me.ipsum_amet.bikeplace.view.bikeEntry.BackAction

@Composable
fun BikeDetailsAppBar(
    navigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            BackAction(onBackClicked = navigateToListScreen)
        },
        title = {},
        actions = {},
    )
}