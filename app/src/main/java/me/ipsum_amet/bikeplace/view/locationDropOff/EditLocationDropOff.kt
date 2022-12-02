package me.ipsum_amet.bikeplace.view.locationDropOff

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import me.ipsum_amet.bikeplace.util.XL_PADDING
import me.ipsum_amet.bikeplace.ui.theme.BikePlaceTheme
import me.ipsum_amet.bikeplace.R
import me.ipsum_amet.bikeplace.util.BIKE_CARD_WIDTH
import me.ipsum_amet.bikeplace.util.M_PADDING

@Composable
fun EditLocationDropOff(
    title: String,
    onTitleChange: (String) -> Unit,
    area: String,
    onAreaChange: (String) -> Unit,
    streetName: String,
    onStreetNameChange: (String) -> Unit,
    moreInfo: String,
    onMoreInfoChange: (String) -> Unit,
    successTextButton: String,
    onSuccessTextButtonClicked: () -> Unit,
    dialogState: MutableState<Boolean>
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(BIKE_CARD_WIDTH, MaterialTheme.colors.surface),
        modifier = Modifier
            .wrapContentSize()

    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = M_PADDING)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("Edit Address", fontWeight = FontWeight.Bold)
                IconButton(onClick = {
                    dialogState.value = false
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.cancel_icon)
                    )
                }
            }
            Spacer(modifier = Modifier.height(XL_PADDING))
            OutlinedTextField(
                value = title,
                onValueChange = { onTitleChange(it) },
                label = { Text(stringResource(id = R.string.title)) },
                singleLine = true,
                placeholder = { Text(stringResource(id = R.string.enter_title)) },
            )
            Spacer(modifier = Modifier.height(XL_PADDING))
            OutlinedTextField(
                value = area,
                onValueChange = { onAreaChange(it) },
                label = { Text(text = stringResource(id = R.string.area)) },
                singleLine = true,
                placeholder = { Text(text = stringResource(id = R.string.enter_area)) },
            )
            Spacer(modifier = Modifier.height(XL_PADDING))
            OutlinedTextField(
                value = streetName,
                onValueChange = { onStreetNameChange(it) },
                label = { Text(text = stringResource(id = R.string.street_name)) },
                singleLine = true,
                placeholder = { Text(text = stringResource(id = R.string.enter_street_name)) }
            )
            Spacer(modifier = Modifier.height(XL_PADDING))
            OutlinedTextField(
                value = moreInfo,
                onValueChange =  {onMoreInfoChange(it) },
                label = { Text(text = stringResource(id = R.string.more_info)) },
                singleLine = false,
                placeholder = { Text(text = stringResource(id = R.string.enter_more_info)) }
            )
            Spacer(modifier = Modifier.height(XL_PADDING))
            TextButton(
                onClick = onSuccessTextButtonClicked,
                modifier = Modifier
                    .align(CenterHorizontally)
            ) {
                Text(text = successTextButton)
            }
        }
    }
}

@Composable
fun CompleteDialogContent(
    title: String,
    dialogState: MutableState<Boolean>,
    successButtonText: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth(1f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TitleAndButton(title, dialogState)
            AddBody(content)
            BottomButtons(successButtonText, dialogState = dialogState)
        }
    }
}


@Composable
private fun TitleAndButton(title: String, dialogState: MutableState<Boolean>) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, fontSize = 24.sp)
            IconButton(modifier = Modifier.then(Modifier.size(24.dp)),
                onClick = {
                    dialogState.value = false
                }) {
                Icon(
                    Icons.Filled.Close,
                    "contentDescription"
                )
            }
        }
        Divider(color = Color.DarkGray, thickness = 1.dp)
    }
}

@Composable
private fun BottomButtons(successButtonText: String, dialogState: MutableState<Boolean>) {
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxWidth(1f)
            .padding(20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .width(100.dp)
                .padding(end = 5.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "Cancel", fontSize = 20.sp)
        }
        Button(
            onClick = {
                dialogState.value = false
            },
            modifier = Modifier.width(100.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = successButtonText, fontSize = 20.sp)
        }

    }
}

@Composable
private fun AddBody(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .padding(20.dp)
    ) {
        content()
    }
}

@Preview("EditLocationDropOff", showSystemUi = true, showBackground = true)
@Composable
fun PEditLocationDropOff() {
    val dialogState = remember {mutableStateOf(false)}
    BikePlaceTheme() {
        EditLocationDropOff(
            title = "Office",
            onTitleChange = {},
            area = "Nyeri",
            onAreaChange = {},
            streetName = "#532943",
            onStreetNameChange = {},
            moreInfo = "Room #1 Ground Floor, FireStone Building Block A dfdferwrwqererewrqwerqrerqerqrererwqwerqerrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr",
            onMoreInfoChange = {},
            successTextButton = "Save",
            onSuccessTextButtonClicked = {},
            dialogState = dialogState
        )
    }
}