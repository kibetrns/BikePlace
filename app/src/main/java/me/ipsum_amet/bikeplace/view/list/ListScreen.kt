package me.ipsum_amet.bikeplace.view.list

import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel
import me.ipsum_amet.bikeplace.R
import me.ipsum_amet.bikeplace.Util.Action
import me.ipsum_amet.bikeplace.components.BottomNavBar

@Composable
fun ListScreen(
    navigateToBikeScreen: (String) -> Unit,
    bikePlaceViewModel: BikePlaceViewModel,
    navController: NavHostController
) {


    LaunchedEffect(key1 = true) {
        bikePlaceViewModel.getAllBikes()
    }

    val searchAppBarState by bikePlaceViewModel.searchAppBarState
    val searchTextState by bikePlaceViewModel.searchTextState

    val action by bikePlaceViewModel.action

    val scaffoldState = rememberScaffoldState()

    val allBikes by bikePlaceViewModel.allBikes.collectAsState()
    Log.d("lSAllBikes", allBikes.toString())
    val searchedBikes by bikePlaceViewModel.searchedBikes.collectAsState()
    Log.d("lSSearchedBikes", searchedBikes.toString())

    val allBikesByCategory by bikePlaceViewModel.allBikesByCategory.collectAsState()
    Log.d("lSAllBikesByCategory", allBikesByCategory.toString())

    DisplaySnackBar(
        scaffoldState = scaffoldState,
        handleDatabaseActions = { bikePlaceViewModel.handleDatabaseAction(action = action) },
        onUndoClicked = {
                        bikePlaceViewModel.action.value = it
        },
        taskTitle = bikePlaceViewModel.bikeName.value,
        action = action
    )

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BottomNavBar(navController = navController) },
        topBar = {
            ListAppBar(
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState,
                bikePlaceViewModel = bikePlaceViewModel
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToBikeScreen,bikePlaceViewModel)
        }
    ) {
        ListContent(
            allBikes = allBikes,
            searchedBikes = searchedBikes,
            searchAppBarState = searchAppBarState,
            navigateToBikeScreen = navigateToBikeScreen
        )
    }
}



@Composable
fun ListFab(onFabClicked: (String) -> Unit, bikePlaceViewModel: BikePlaceViewModel) {
    FloatingActionButton(onClick = {
        bikePlaceViewModel.updateSelectedBikeState()
        onFabClicked("FIRST")
    }) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(id = R.string.add_FAB)
        )
    }
}
@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    handleDatabaseActions: () -> Unit,
    onUndoClicked: (Action) -> Unit,
    taskTitle: String,
    action: Action
) {
    handleDatabaseActions()

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = setMessage(action = action, taskTitle = taskTitle),
                    actionLabel = setActionLabel(action = action)
                )
                logoutOfAccount(
                    action = action,
                    snackBarResult = snackBarResult,
                    onUndoCLicked = onUndoClicked
                )
            }
        }
    }
}

private fun setMessage(action: Action, taskTitle: String): String {
    return when(action) {
        Action.LOG_OUT -> "Logging Out"
        else -> "${action.name} -> $taskTitle"
    }

}

private fun setActionLabel(action: Action): String {
    return if (action.name == "DELETE") {
        "UNDO"
    } else {
        "OK"
    }
}


private fun logoutOfAccount(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoCLicked: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed
        && action == Action.LOG_OUT
    ) {
        onUndoCLicked(Action.UNDO)
    }
}