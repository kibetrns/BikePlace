package me.ipsum_amet.bikeplace.navigation

import androidx.navigation.NavController
import me.ipsum_amet.bikeplace.Util.Action
import me.ipsum_amet.bikeplace.Util.LIST_SCREEN
import me.ipsum_amet.bikeplace.Util.SIGNUP_SCREEN

class Screens(navController: NavController) {

    val register: () -> Unit = {
        navController.navigate(route = "signUp") {

        }
    }

    val signUp: (Action) -> Unit = {  action: Action -> Unit
        navController.navigate("list/${action.name}") {
            popUpTo(SIGNUP_SCREEN) {
                inclusive = true
            }
        }
    }

    val list: (Action) -> Unit = { action: Action ->
        navController.navigate("list/${action.name}") {
            popUpTo(LIST_SCREEN) {
                inclusive = true
            }
        }
    }

    val bike: (Int) -> Unit = { bikeId: Int ->
        navController.navigate("bike/$bikeId")
    }
}