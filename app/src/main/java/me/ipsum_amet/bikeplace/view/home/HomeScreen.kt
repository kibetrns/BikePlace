package me.ipsum_amet.bikeplace.view.home

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import me.ipsum_amet.bikeplace.Util.Action
import me.ipsum_amet.bikeplace.Util.GetCategoryState
import me.ipsum_amet.bikeplace.data.model.User
import me.ipsum_amet.bikeplace.view.list.DisplaySnackBar
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

@Composable
fun HomeScreen(
    bikePlaceViewModel: BikePlaceViewModel,
    navigateToBikeDetailsScreen: (bikeId: String) -> Unit,
    ) {

    LaunchedEffect(key1 = true, ) {
        bikePlaceViewModel.getTopChoiceBikes()
    }
    LaunchedEffect(key1 = true) {
        bikePlaceViewModel.getAllBikeCategories()
    }
    val action by bikePlaceViewModel.action
    val scaffoldState = rememberScaffoldState()

    val user by bikePlaceViewModel.user
    val topBikeChoices by bikePlaceViewModel.topBikeChoices.collectAsState()
    val allBikeCategories by bikePlaceViewModel.allBikeCategories.collectAsState()

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
        topBar = {
            HomeAppBar()
        },
        bottomBar = {
                   // BottomNavBar(navController = )
        },
        content = {
            user?.let { user: User ->
                HomeContent(
                    user = user,
                    topChoiceBikes = topBikeChoices,
                    allBikeCategories = allBikeCategories,
                    onCHomeBikeClicked = {
                        bikePlaceViewModel.action.value = Action.GET_ALL_BIKES_BY_CATEGORY
                        bikePlaceViewModel.getCategoryState.value = GetCategoryState.TRIGGERED
                    },
                    navigateToBikeDetailsScreen = navigateToBikeDetailsScreen
                )
            }
        }
    )
}