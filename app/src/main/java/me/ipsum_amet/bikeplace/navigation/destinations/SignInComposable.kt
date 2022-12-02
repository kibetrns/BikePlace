package me.ipsum_amet.bikeplace.navigation.destinations

import SignInScreen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import me.ipsum_amet.bikeplace.util.Action
import me.ipsum_amet.bikeplace.util.SIGNIN_SCREEN
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

fun NavGraphBuilder.signInComposable(
    navigateToRegisterScreen: () -> Unit,
    navigateToListScreen: () -> Unit,
    navigateToResetPasswordScreen: () -> Unit,
    bikePlaceViewModel: BikePlaceViewModel
) {
    composable(
        route = SIGNIN_SCREEN,
    ) {
        SignInScreen(
            navigateToRegisterScreen = navigateToRegisterScreen,
            navigateToHomeScreen = navigateToListScreen,
            navigateToResetPasswordScreen = navigateToResetPasswordScreen,
            bikePlaceViewModel = bikePlaceViewModel
        )
    }
}

