package me.ipsum_amet.bikeplace.view.search

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import me.ipsum_amet.bikeplace.components.BottomNavBar
import me.ipsum_amet.bikeplace.view.list.ListAppBar
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

@Composable
fun SearchScreen(
    navigateToBikeDetailsScreen: (String) -> Unit,
    navController: NavHostController,
    bikePlaceViewModel: BikePlaceViewModel
) {

    LaunchedEffect(key1 = true ) {
        bikePlaceViewModel.getAllBikes()
    }

    val searchAppBarState by bikePlaceViewModel.searchAppBarState
    val searchTextState by bikePlaceViewModel.searchTextState


    val allBikes by bikePlaceViewModel.allBikes.collectAsState()
    val searchedBikes by bikePlaceViewModel.searchedBikes.collectAsState()


    Scaffold(
        topBar = { ListAppBar(
            searchAppBarState = searchAppBarState,
            searchTextState = searchTextState,
            bikePlaceViewModel = bikePlaceViewModel
        ) },
        bottomBar = { BottomNavBar(navController = navController) }
    ) {
        SearchContent(
            allBikes = allBikes,
            searchedBikes = searchedBikes,
            searchAppBarState = searchAppBarState,
            navigateToBikeScreen = navigateToBikeDetailsScreen
        )
    }
}