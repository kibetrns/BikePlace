package me.ipsum_amet.bikeplace.view.list


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel
import me.ipsum_amet.bikeplace.R
import me.ipsum_amet.bikeplace.Util.*
import me.ipsum_amet.bikeplace.components.DisplayAlertDialog
import me.ipsum_amet.bikeplace.components.ConditionItem
import me.ipsum_amet.bikeplace.data.model.CONDITION
import me.ipsum_amet.bikeplace.ui.theme.BikePlaceTheme


@Composable
fun ListAppBar(
    searchAppBarState: SearchAppBarState,
    searchTextState: String,
    bikePlaceViewModel: BikePlaceViewModel
) {
    when(searchAppBarState) {
        SearchAppBarState.CLOSED  -> {
            DefaultListAppBar(
                onSearchClicked = {
                    bikePlaceViewModel.searchAppBarState.value = SearchAppBarState.OPENED
                },
                onSortClicked = {
                    bikePlaceViewModel.searchAppBarState.value = SearchAppBarState.CLOSED
                    bikePlaceViewModel.searchTextState.value = ""
                },
                onDeleteAllConfirmed = {
                    bikePlaceViewModel.action.value = Action.DELETE_ALL
                }
            )
        }
        else -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = { newText ->
                    bikePlaceViewModel.searchTextState.value = newText
                },
                onCloseClicked = {
                    bikePlaceViewModel.searchAppBarState.value = SearchAppBarState.CLOSED
                    bikePlaceViewModel.searchTextState.value = ""
                },
                onSearchClicked = {
                    bikePlaceViewModel.searchAppBarState.value = SearchAppBarState.TRIGGERED
                    bikePlaceViewModel.searchDB(it)
                }
            )
        }
    }
}

@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (condition: CONDITION) -> Unit,
    onDeleteAllConfirmed: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.default_screen_title))
        },
        actions = {
            ListAppBarActions(
                onSearchClicked = onSearchClicked,
                onSortClicked = onSortClicked,
                onDeleteAllConfirmed = onDeleteAllConfirmed
            )
        },
    )
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    var trailingIconState by remember { mutableStateOf(TrailingIconState.READY_TO_DELETE)}


    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT),
        elevation = AppBarDefaults.TopAppBarElevation,


        ) {
        TextField(
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search),
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .alpha(ContentAlpha.medium)
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colors.onBackground,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = R.string.search_task_icon)
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        when(trailingIconState){
                            TrailingIconState.READY_TO_DELETE -> {
                                onTextChange("")
                                trailingIconState = TrailingIconState.READY_TO_CLOSE
                            }
                            TrailingIconState.READY_TO_CLOSE -> {
                                if (text.isNotEmpty()){
                                    onTextChange("")
                                } else {
                                    onCloseClicked()
                                    trailingIconState = TrailingIconState.READY_TO_DELETE
                                }
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.close_icon)
                    )

                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            )
        )
    }


}

@Composable
fun ListAppBarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (condition: CONDITION) -> Unit,
    onDeleteAllConfirmed: () -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }
    DisplayAlertDialog(
        title = stringResource(id = R.string.log_out),
        message = stringResource(id = R.string.log_out_confirmation),
        openDialog = openDialog,
        closeDialog = { openDialog = false },
        onYesClicked = { onDeleteAllConfirmed() }
    )

    SearchAction(onSearchClicked = onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    LogOutAction(onDeleteAllConfirmed = { openDialog = true })
}

@Composable
fun SearchAction(onSearchClicked: () -> Unit) {
    IconButton(onClick = onSearchClicked) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(id = R.string.search_task_icon)
        )

    }

}

@Composable
fun SortAction(onSortClicked: (Condition: CONDITION) -> Unit) {
    var expanded by remember { mutableStateOf(false)}

    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(id = R.drawable.filter_list),
            contentDescription = stringResource(id = R.string.filter_tasks_icon)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {
                expanded = false
                onSortClicked(CONDITION.EXCELLENT)
            }) {
                ConditionItem(condition = CONDITION.EXCELLENT)
            }
            DropdownMenuItem(onClick = {
                expanded = false
                onSortClicked(CONDITION.GOOD)
            }) {
                ConditionItem(condition = CONDITION.GOOD)
            }
            DropdownMenuItem(onClick = {
                expanded = false
                onSortClicked(CONDITION.AVERAGE)
            }) {
                ConditionItem(condition = CONDITION.AVERAGE)
            }
            DropdownMenuItem(onClick = {
                expanded = false
                onSortClicked(CONDITION.BAD)
            }) {
                ConditionItem(condition = CONDITION.BAD)
            }
            DropdownMenuItem(onClick = {
                expanded = false
                onSortClicked(CONDITION.POOR)
            }) {
                ConditionItem(condition = CONDITION.POOR)
            }
        }
    }
}

@Composable
fun LogOutAction(onDeleteAllConfirmed: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = stringResource(id = R.string.filter_tasks_icon)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    expanded = true
                    onDeleteAllConfirmed()
                }) {
                Text(
                    text = stringResource(id = R.string.log_out),
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(start = L_PADDING)
                )
            }
        }
    }
}

@Preview(name = "DefaultListAppBar")
@Composable
fun PDefaultListAppBar() {
    BikePlaceTheme() {
        DefaultListAppBar(
            onSearchClicked = {},
            onSortClicked = {},
            onDeleteAllConfirmed = {}
        )
    }
}

@Preview(name = "SearchAppBar")
@Composable
fun PSearchAppBar() {
    BikePlaceTheme() {
        SearchAppBar(
            text = "Search",
            onTextChange = {},
            onCloseClicked = { },
            onSearchClicked = {},
        )
    }
}













