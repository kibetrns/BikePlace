package me.ipsum_amet.bikeplace.components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import me.ipsum_amet.bikeplace.util.Action
import me.ipsum_amet.bikeplace.view.bikeEntry.BackAction

@Composable
fun PlainAppBar(
    title: String,
    navigateToPreviousScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            BackAction(onBackClicked = navigateToPreviousScreen)
        },
        title = {
            Text(text = title)
        },
        actions = {},
    )
}