package me.ipsum_amet.bikeplace.view.locationDropOff

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import me.ipsum_amet.bikeplace.util.Action
import me.ipsum_amet.bikeplace.view.bikeDetails.PlainAppBar
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

@Composable
fun LocationDropOffScreen(
    navigateToPreviousScreen: (Action) -> Unit,
    navigateToSummaryScreen: () -> Unit,
    bikePlaceViewModel: BikePlaceViewModel
) {
    val ctx = LocalContext.current
    val dialogState = remember {
        mutableStateOf(false)
    }
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true,) {
        bikePlaceViewModel.user
    }

    val userDetails by bikePlaceViewModel.userDetails.collectAsState()


    var title by remember { bikePlaceViewModel.title }
    var area by remember { bikePlaceViewModel.area }
    var streetName by remember { bikePlaceViewModel.streetName }
    var moreInfo by remember { bikePlaceViewModel.moreInfo }
    var geoPoint by remember { bikePlaceViewModel.geoPoint }



    Scaffold(
        topBar = {
            PlainAppBar(title = "Location Drop Off") { action: Action ->
                if (action == Action.NO_ACTION)
                    navigateToPreviousScreen(action)
            }
        },
     content = {
         if (dialogState.value) {
             Dialog(
                 onDismissRequest = { dialogState.value = false },
                 properties = DialogProperties(
                     dismissOnBackPress = false,
                     dismissOnClickOutside = true
                 )
             ) {
                 title?.let { it1 ->
                     area?.let { it2 ->
                         streetName?.let { it3 ->
                             moreInfo?.let { it4 ->
                                 EditLocationDropOff(
                                     title = it1,
                                     onTitleChange = { bikePlaceViewModel.title.value = it },
                                     area = it2,
                                     onAreaChange = { bikePlaceViewModel.area.value = it },
                                     streetName = it3,
                                     onStreetNameChange = { bikePlaceViewModel.streetName.value = it },
                                     moreInfo = it4,
                                     onMoreInfoChange = { bikePlaceViewModel.moreInfo.value = it },
                                     successTextButton = "Save",
                                     onSuccessTextButtonClicked = {

                                     },
                                     dialogState = dialogState
                                 )
                             }
                         }
                     }
                 }
             }
         }

         userDetails?.let { user ->
             title?.let { it1 ->
                 area?.let { it2 ->
                     streetName?.let { it3 ->
                         moreInfo?.let { it4 ->
                             LocationDropOffContent(
                                 user = user,
                                 title = it1,
                                 area = it2,
                                 streetName = it3,
                                 moreInfo = it4,
                                 geoPoint = null,
                                 navigateToEditAddressPopUp = {
                                     dialogState.value = it
                                 },
                                 navigateToSummaryScreen = {
                                     navigateToSummaryScreen()
                                 }
                             )
                         }
                     }
                 }
             }
         }
     }
    )
}
/*
@Preview
@Composable
fun PLocationDropOffScreen() {
    LocationDropOffScreen(navigateToPreviousScreen = {}, )
}

 */