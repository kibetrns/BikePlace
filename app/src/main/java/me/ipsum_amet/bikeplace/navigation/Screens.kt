package me.ipsum_amet.bikeplace.navigation

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.navDeepLink
import me.ipsum_amet.bikeplace.util.*
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

class Screens(navController: NavController) {

    val register: () -> Unit = {
        navController.navigate(route = "signIn") {
            popUpTo(REGISTER_SCREEN) { inclusive = true }
            launchSingleTop = true
        }
    }

    val signIn: () -> Unit = {
        navController.navigate(route = "register") {
            popUpTo(SIGNIN_SCREEN) { inclusive = true }
            launchSingleTop = true

        }
    }

    val reset: () -> Unit = {
       navController.navigate(route = "resetPassword") {
           popUpTo(RESET_PASSWORD_SCREEN) { inclusive = true }
           launchSingleTop = true

       }
    }

    val list: (String) -> Unit = { bikeId: String ->
        navController.navigate("bike/$bikeId") {
            popUpTo(HOME_SCREEN) { inclusive = true }
            launchSingleTop = true

        }
    }
    val list2: (String) -> Unit = { bikeId: String ->
        navController.navigate("bikeDetails/$bikeId") {
            popUpTo(HOME_SCREEN) { inclusive = true }
            launchSingleTop = true
        }
    }


    val bike: (Action) -> Unit = { action: Action ->
        navController.navigate("list/${action.name}") {
            popUpTo(HOME_SCREEN) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }


    val category: (Action) -> Unit = { action: Action ->
        navController.navigate("categoryList/$action") {
            popUpTo(HOME_SCREEN) { inclusive = true }
            launchSingleTop =true
        }
    }

    val home: (Action) -> Unit = { action: Action ->
        navController.navigate("home/?${action.name}") {
            popUpTo(HOME_SCREEN) { inclusive = true }
            launchSingleTop = true
        }
    }
}