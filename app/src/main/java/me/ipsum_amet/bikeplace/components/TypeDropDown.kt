package me.ipsum_amet.bikeplace.components

import me.ipsum_amet.bikeplace.data.model.CONDITION
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.ipsum_amet.bikeplace.Util.PRIORITY_DROPDOWN_HEIGHT
import me.ipsum_amet.bikeplace.R
import me.ipsum_amet.bikeplace.Util.S_PADDING
import me.ipsum_amet.bikeplace.data.model.TYPE
import me.ipsum_amet.bikeplace.ui.theme.BikePlaceTheme

@Composable
fun TypeDropDown(
    type: TYPE,
    onTypeSelected:(TYPE) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val angle: Float by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .height(PRIORITY_DROPDOWN_HEIGHT)
            .clickable { expanded = true }
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
                shape = MaterialTheme.shapes.small
            )
            .padding(start = S_PADDING),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = type.name,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier
                .weight(8f)
        )
        IconButton(
            onClick = { expanded = true },
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .rotate(degrees = angle)
                .weight(1.5f)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = stringResource(id = R.string.drop_down_icon),
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(fraction = 0.94f)
            ) {
                DropdownMenuItem(onClick = {
                    expanded = false
                    onTypeSelected(TYPE.BMX)
                }) {
                    TypeItem(type = TYPE.BMX)
                }
                DropdownMenuItem(onClick = {
                    expanded = false
                    onTypeSelected(TYPE.CRUISER)
                }) {
                    TypeItem(type = TYPE.CRUISER)
                }
                DropdownMenuItem(onClick = {
                    expanded = false
                    onTypeSelected(TYPE.CYCLOCROSS_BIKE)
                }) {
                    TypeItem(type = TYPE.CYCLOCROSS_BIKE)
                }
                DropdownMenuItem(onClick = {
                    expanded = false
                    onTypeSelected(TYPE.ELECTRIC_BIKE)
                }) {
                    TypeItem(type = TYPE.ELECTRIC_BIKE)
                }
                DropdownMenuItem(onClick = {
                    expanded = false
                    onTypeSelected(TYPE.FOLDING_BIKE)
                }) {
                    TypeItem(type = TYPE.FOLDING_BIKE)
                }
                DropdownMenuItem(onClick = {
                    expanded = false
                    onTypeSelected(TYPE.HYBRID_BIKE)
                }) {
                    TypeItem(type = TYPE.HYBRID_BIKE)
                }
                DropdownMenuItem(onClick = {
                    expanded = false
                    onTypeSelected(TYPE.MOUNTAIN_BIKE)
                }) {
                    TypeItem(type = TYPE.MOUNTAIN_BIKE)
                }
                DropdownMenuItem(onClick = {
                    expanded = false
                    onTypeSelected(TYPE.RECUMBENT_BIKE)
                }) {
                    TypeItem(type = TYPE.RECUMBENT_BIKE)
                }
                DropdownMenuItem(onClick = {
                    expanded = false
                    onTypeSelected(TYPE.ROAD_RIDE)
                }) {
                    TypeItem(type = TYPE.ROAD_RIDE)
                }
                DropdownMenuItem(onClick = {
                    expanded = false
                    onTypeSelected(TYPE.TOURING_BIKE)
                }) {
                    TypeItem(type = TYPE.TOURING_BIKE)
                }
                DropdownMenuItem(onClick = {
                    expanded = false
                    onTypeSelected(TYPE.TRACK_BIKE)
                }) {
                    TypeItem(type = TYPE.TRACK_BIKE)
                }
            }
        }
    }
}

@Preview("TypeDropDown")
@Composable
fun PTypeDropDown() {
    BikePlaceTheme() {
        TypeDropDown(type = TYPE.MOUNTAIN_BIKE, onTypeSelected = {})
    }

}