package me.ipsum_amet.bikeplace.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.ipsum_amet.bikeplace.util.XL_SIZE

@Composable
fun ProgressBox() {
    Box(
        modifier = Modifier.then(Modifier.size(XL_SIZE))
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}