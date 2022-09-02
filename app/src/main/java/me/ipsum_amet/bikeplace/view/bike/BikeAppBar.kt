package me.ipsum_amet.bikeplace.view.bike

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import me.ipsum_amet.bikeplace.Util.Action
import me.ipsum_amet.bikeplace.components.DisplayAlertDialog
import me.ipsum_amet.bikeplace.data.model.Bike
import me.ipsum_amet.bikeplace.R
import me.ipsum_amet.bikeplace.ui.theme.BikePlaceTheme


@Composable
fun BikeAppBar(
    selectedBike: Bike?,
    navigateToListScreen: (Action) -> Unit
) {
    if (selectedBike == null) {
        NewTaskAppBar(navigateToListScreen = navigateToListScreen)
    } else {
        ExistingTaskAppBar(selectedBike = selectedBike, navigateToListScreen = navigateToListScreen)
    }

}
@Composable
fun NewTaskAppBar(
    navigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            BackAction(onBackClicked = navigateToListScreen)
        },
        title = {
            Text(text = stringResource(id = R.string.post_bike_title))
        },
        actions = {
            AddAction(onAddClicked = navigateToListScreen)
        }
    )
}


@Composable
fun ExistingTaskAppBar(
    selectedBike: Bike,
    navigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            CloseAction(onCloseClicked = navigateToListScreen)
        },
        title = {
            selectedBike.name?.let {
                Text(
                    text = it,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        actions = {
            ExistingTaskAppBarActions(
                selectedBike = selectedBike,
                navigateToListScreen = navigateToListScreen
            )
        }
    )
}

@Composable
fun ExistingTaskAppBarActions(
    selectedBike: Bike,
    navigateToListScreen: (Action) -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }
    selectedBike.name?.let {
        DisplayAlertDialog(
            title = stringResource(id = R.string.delete_bike, it),
            message = stringResource(id = R.string.delete_bike_confirmation, it),
            openDialog = openDialog,
            closeDialog = { openDialog = false },
            onYesClicked = {
                navigateToListScreen(Action.DELETE)
            }
        )
    }
    DeleteAction(onDeleteClicked = { openDialog = true })
    UpdateAction(onUpdateClicked = navigateToListScreen)
}


@Composable
fun CloseAction(onCloseClicked: (Action) ->Unit ) {
    IconButton(
        onClick = { onCloseClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = stringResource(id = R.string.close_icon)
        )
    }
}

@Composable
fun DeleteAction(onDeleteClicked: () ->Unit ) {
    IconButton(
        onClick = { onDeleteClicked() }) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = stringResource(id = R.string.delete_icon)
        )
    }
}

@Composable
fun UpdateAction(onUpdateClicked: (Action) ->Unit ) {
    IconButton(
        onClick = { onUpdateClicked(Action.UPDATE) }) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = stringResource(id = R.string.update_icon)
        )
    }
}


@Composable
fun BackAction(onBackClicked: (Action) ->Unit ) {
    IconButton(
        onClick = { onBackClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(id = R.string.back_arrow)
        )
    }
}

@Composable
fun AddAction(onAddClicked: (Action) -> Unit ) {
    IconButton(
        onClick = { onAddClicked(Action.ADD) }
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = stringResource(id = R.string.post_bike_title)
        )
    }
}

@Preview("NewBikeBar")
@Composable
fun PNewTaskAppBar() {
    BikePlaceTheme() {
        NewTaskAppBar(navigateToListScreen = {})

    }

}


@Preview("ExistingBikeBar")
@Composable
fun PExistingTaskAppBar() {
    BikePlaceTheme() {
        ExistingTaskAppBar(selectedBike = Bike(), navigateToListScreen = {})
    }
}
