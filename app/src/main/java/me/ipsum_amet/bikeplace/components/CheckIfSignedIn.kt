package me.ipsum_amet.bikeplace.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

@Composable
fun CheckIfSignedIn(bikePlaceViewModel: BikePlaceViewModel, navController: NavController) {
    val alreadyLoggedIn = remember { mutableStateOf(false) }
    val signedIn = bikePlaceViewModel.signedIn.value

    if (signedIn && !alreadyLoggedIn.value) {
        alreadyLoggedIn.value = true
        navController.navigate("") {
            popUpTo(0)
        }
    }
    
}