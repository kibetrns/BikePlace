package me.ipsum_amet.bikeplace.components

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
fun ConditionItem(condition: CONDITION) {
    Text(
        modifier = Modifier
            .padding(start = L_PADDING),
        text = condition.name,
        style = MaterialTheme.typography.subtitle1,
        color = MaterialTheme.colors.onSurface
    )
}

@Preview(name = "ConditionItem", showBackground = true)
@Composable
fun PTConditionItem() {
    BikePlaceTheme() {
        ConditionItem(condition = CONDITION.EXCELLENT)
    }
}