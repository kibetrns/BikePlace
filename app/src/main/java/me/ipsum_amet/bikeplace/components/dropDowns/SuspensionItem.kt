package me.ipsum_amet.bikeplace.components.dropDowns

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.ipsum_amet.bikeplace.data.model.GroupSetMaterial
import me.ipsum_amet.bikeplace.data.model.HandleBars
import me.ipsum_amet.bikeplace.data.model.Suspension
import me.ipsum_amet.bikeplace.ui.theme.BikePlaceTheme
import me.ipsum_amet.bikeplace.util.L_PADDING

@Composable
fun SuspensionItem(suspension: Suspension) {
    Text(
        modifier = Modifier
            .padding(start = L_PADDING),
        text = suspension.name,
        style = MaterialTheme.typography.subtitle1,
        color = MaterialTheme.colors.onSurface
    )
}

@Preview(name = "SuspensionItem", showBackground = true)
@Composable
fun PSuspensionItem() {
    BikePlaceTheme() {
        SuspensionItem(suspension = Suspension.FULL_SUSPENSION)
    }
}