package me.ipsum_amet.bikeplace.view.bikeDetails

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import me.ipsum_amet.bikeplace.R
import me.ipsum_amet.bikeplace.Util.Action
import me.ipsum_amet.bikeplace.view.bikeEntry.AddAction
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