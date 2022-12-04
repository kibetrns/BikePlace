package me.ipsum_amet.bikeplace.components.dropDowns

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.ipsum_amet.bikeplace.data.model.CONDITION
import me.ipsum_amet.bikeplace.data.model.GroupSetMaterial
import me.ipsum_amet.bikeplace.ui.theme.BikePlaceTheme
import me.ipsum_amet.bikeplace.util.L_PADDING

@Composable
fun GroupItem(groupSetMaterial: GroupSetMaterial) {
    Text(
        modifier = Modifier
            .padding(start = L_PADDING),
        text = groupSetMaterial.name,
        style = MaterialTheme.typography.subtitle1,
        color = MaterialTheme.colors.onSurface
    )
}

@Preview(name = "GroupSetMaterialItem", showBackground = true)
@Composable
fun PTConditionItem() {
    BikePlaceTheme() {
        GroupItem(groupSetMaterial = GroupSetMaterial.CARBON_FIBRE)
    }
}