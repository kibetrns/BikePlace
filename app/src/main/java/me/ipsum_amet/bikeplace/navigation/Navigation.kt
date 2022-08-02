package me.ipsum_amet.bikeplace.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import me.ipsum_amet.bikeplace.Util.REGISTER_SCREEN
import me.ipsum_amet.bikeplace.navigation.destinations.bikeComposable
import me.ipsum_amet.bikeplace.navigation.destinations.listComposable
import me.ipsum_amet.bikeplace.navigation.destinations.registerComposable
import me.ipsum_amet.bikeplace.navigation.destinations.signUpComposable

@Composable
fun SetUpNavigation(navController: NavHostController) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }
    
    NavHost(navController = navController, startDestination = REGISTER_SCREEN) {
        registerComposable(
            navigateToSignUpScreen = screen.signUp
        )
        signUpComposable(
            navigateToListScreen = screen.list
        )
        listComposable(
            navigateToBikeScreen = screen.bike
        )
        bikeComposable(navigateToListScreen = screen.list)



    }


}