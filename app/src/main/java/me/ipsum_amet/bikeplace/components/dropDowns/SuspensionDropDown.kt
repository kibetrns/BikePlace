package me.ipsum_amet.bikeplace.components.dropDowns

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
import androidx.compose.ui.unit.dp
import me.ipsum_amet.bikeplace.R
import me.ipsum_amet.bikeplace.data.model.GroupSetMaterial
import me.ipsum_amet.bikeplace.data.model.HandleBars
import me.ipsum_amet.bikeplace.data.model.Suspension
import me.ipsum_amet.bikeplace.util.PRIORITY_DROPDOWN_HEIGHT
import me.ipsum_amet.bikeplace.util.S_PADDING

@Composable
fun SuspensionDropDown(
    suspension: Suspension,
    onHandleBarsSelected:(Suspension) -> Unit
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
            text = suspension.name,
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
                    onHandleBarsSelected(Suspension.FULL_SUSPENSION)
                }) {
                    SuspensionItem(suspension = Suspension.FULL_SUSPENSION)
                }
                DropdownMenuItem(onClick = {
                    expanded = false
                    onHandleBarsSelected(Suspension.HARD_TAIL)
                }) {
                    SuspensionItem(suspension = Suspension.HARD_TAIL)
                }
            }
        }
    }
}