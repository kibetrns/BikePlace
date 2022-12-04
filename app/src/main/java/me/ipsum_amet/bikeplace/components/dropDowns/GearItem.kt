package me.ipsum_amet.bikeplace.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.ipsum_amet.bikeplace.data.model.CONDITION
import me.ipsum_amet.bikeplace.data.model.Gears
import me.ipsum_amet.bikeplace.ui.theme.BikePlaceTheme
import me.ipsum_amet.bikeplace.util.L_PADDING

@Composable
fun GearItem(gears: Gears) {
    Text(
        modifier = Modifier
            .padding(start = L_PADDING),
        text = gears.name,
        style = MaterialTheme.typography.subtitle1,
        color = MaterialTheme.colors.onSurface
    )
}

@Preview(name = "GearItem", showBackground = true)
@Composable
fun PTGearItem() {
    BikePlaceTheme() {
        GearItem(gears = Gears.ABOVE_TWENTY)
    }
}