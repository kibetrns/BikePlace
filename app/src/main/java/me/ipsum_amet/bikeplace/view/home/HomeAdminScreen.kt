package me.ipsum_amet.bikeplace.view.home

import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import me.ipsum_amet.bikeplace.components.BottomNavBar
import me.ipsum_amet.bikeplace.util.SearchAppBarState
import me.ipsum_amet.bikeplace.view.list.SearchAppBar
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel


@Composable
fun HomeAdminScreen(
    bikePlaceViewModel: BikePlaceViewModel,
    navController: NavHostController,
) {

    val tabTitles = listOf("All", "-> Leased", "<- Returned")

    LaunchedEffect(key1 = true) {
        bikePlaceViewModel.getAllBookingsInfo()
    }

    LaunchedEffect(key1 = true) {
        bikePlaceViewModel.getAllLeasedBookings()
    }

    LaunchedEffect(key1 = true) {
        bikePlaceViewModel.getAllReturnedBookings()
    }


    val allBookingsInfo by bikePlaceViewModel.allBookingsInfoRes.collectAsState()
    val leasedBookingsInfo by bikePlaceViewModel.leasedBookingsInfo.collectAsState()
    val returnedBookingsInfo by bikePlaceViewModel.returnedBookingsInfo.collectAsState()


    val searchedBookingInfo by bikePlaceViewModel.searchedBookingInfo.collectAsState()

    val homeAdminSearchAppBarState by bikePlaceViewModel.homeAdminSearchAppBarState
    val searchReceiptNumber by bikePlaceViewModel.searchReceiptNumber


    val selectedBookingInfoItem by bikePlaceViewModel.selectedBookingInfoItem



    Scaffold(
        topBar = {
            SearchAppBar(
                text = searchReceiptNumber,
                onTextChange = {
                    bikePlaceViewModel.searchReceiptNumber.value = it
                },
                onCloseClicked = {
                    bikePlaceViewModel.homeAdminSearchAppBarState.value = SearchAppBarState.CLOSED
                    bikePlaceViewModel.searchReceiptNumber.value = ""
                },
                onSearchClicked = {
                    bikePlaceViewModel.homeAdminSearchAppBarState.value =
                        SearchAppBarState.TRIGGERED
                    bikePlaceViewModel.getBookingInfoByMpesaReceiptNumber(it)
                }
            )
        },
        bottomBar = { BottomNavBar(navController = navController) },
        content = {
            HomeAdminContent(
                searchedBookingInfo = searchedBookingInfo,
                homeAdminSearchAppBarState = homeAdminSearchAppBarState,
                bookingsInfo = allBookingsInfo,
                leasedBookingsInfo = leasedBookingsInfo,
                returnedBookingsInfo = returnedBookingsInfo,
                tabTitles = tabTitles,
            )
        }
    )
}