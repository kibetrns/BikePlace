package me.ipsum_amet.bikeplace.components

import me.ipsum_amet.bikeplace.data.model.TYPE
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.ipsum_amet.bikeplace.util.L_PADDING
import me.ipsum_amet.bikeplace.data.model.CONDITION
import me.ipsum_amet.bikeplace.ui.theme.BikePlaceTheme

@Composable
fun TypeItem(type: TYPE) {
    Text(
        modifier = Modifier
            .padding(start = L_PADDING),
        text = type.name,
        style = MaterialTheme.typography.subtitle1,
        color = MaterialTheme.colors.onSurface
    )
}

@Preview(name = "TypeItem", showBackground = true)
@Composable
fun PTypeItem() {
    BikePlaceTheme() {
        TypeItem(type = TYPE.BMX)
    }
}