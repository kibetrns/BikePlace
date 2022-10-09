package me.ipsum_amet.bikeplace.view.bikeEntry

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import me.ipsum_amet.bikeplace.Util.Action
import me.ipsum_amet.bikeplace.components.BottomNavBar
import me.ipsum_amet.bikeplace.data.model.Bike
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

@Composable
fun BikeScreen(
    bikePlaceViewModel: BikePlaceViewModel,
    selectedBike: Bike?,
    navController: NavHostController,
    navigateToListScreen: (Action) -> Unit
) {
    val bikeName by remember { bikePlaceViewModel.bikeName }
    val bikeType by remember { bikePlaceViewModel.bikeType }
    val bikeCondition by remember { bikePlaceViewModel.bikeCondition }
    val bikeDescription by remember { bikePlaceViewModel.bikeDescription }
    val bikePrice by remember { bikePlaceViewModel.bikePrice }

    var imageData by  remember{ bikePlaceViewModel.imageData }
    val imageLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
       uri?.let { imageData = uri }
    }


    val context = LocalContext.current


    Scaffold(
        topBar = {
            BikeAppBar(
                selectedBike = selectedBike,
                navigateToListScreen = { action: Action ->
                    if (action == Action.NO_ACTION) {
                        navigateToListScreen(action)
                    } else {
                        if (bikePlaceViewModel.validateBikeContentFields()) {
                            navigateToListScreen(action)
                        } else {
                            displayToast(context = context, "Fill out all the empty and blank fields")
                        }
                    }
                }

            )
        },
        //bottomBar = { BottomNavBar(navController = navController) },
        content = {
            BikeContent(
                name = bikeName,
                onTitleChange = { bikePlaceViewModel.bikeName.value = it },
                description = bikeDescription,
                onDescriptionChange = { bikePlaceViewModel.bikeDescription.value = it },
                type = bikeType,
                onTypeSelected = { bikePlaceViewModel.bikeType.value = it },
                condition = bikeCondition,
                onConditionSelected = { bikePlaceViewModel.bikeCondition.value = it },
                price = bikePrice,
                onPriceChange = { bikePlaceViewModel.bikePrice.value = it },
                imageUrl = imageData.toString(),
                onImageClicked = {
                    imageLauncher.launch("image/*")
                }
            )
        }
    )
}

fun displayToast(context: Context, message: String? = null) {
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT
    ).show()
}