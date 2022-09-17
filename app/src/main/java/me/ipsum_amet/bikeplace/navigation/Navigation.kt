package me.ipsum_amet.bikeplace.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import me.ipsum_amet.bikeplace.Util.REGISTER_SCREEN
import me.ipsum_amet.bikeplace.navigation.destinations.*
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

@Composable
fun SetUpNavigation(navController: NavHostController, bikePlaceViewModel: BikePlaceViewModel) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }

    NavHost(navController = navController, startDestination = REGISTER_SCREEN) {
        registerComposable(
            navigateToSignInScreen = screen.register,
            bikePlaceViewModel = bikePlaceViewModel
        )
        signInComposable(
            navigateToRegisterScreen = screen.signIn,
            navigateToListScreen = screen.bike,
            //navigateToListScreen = screen.home,
            navigateToResetPasswordScreen = screen.reset,
            bikePlaceViewModel = bikePlaceViewModel,
        )
        resetPasswordComposable(
            navigateToRegisterScreen = screen.signIn
        )
        listComposable(
            bikePlaceViewModel = bikePlaceViewModel,
            navigateToBikeScreen = screen.list,
        )
        bikeComposable(
            navigateToListScreen = screen.bike,
            bikePlaceViewModel = bikePlaceViewModel
        )
        bikeDetailsComposable(
            navigateToListScreen = screen.bike,
            bikePlaceViewModel = bikePlaceViewModel
        )
        homeComposable(
            bikePlaceViewModel = bikePlaceViewModel,
            navigateToBikeDetailsScreen = screen.list2,
        )
        categoryListComposable(
            bikePlaceViewModel = bikePlaceViewModel,
            navigateToBikeDetailsScreen = screen.list2
        )
    }
}