package me.ipsum_amet.bikeplace.view.home

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import me.ipsum_amet.bikeplace.R

@Composable
fun HomeAppBar() {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.default_screen_title))
        },
        actions = {

        }
    )


}