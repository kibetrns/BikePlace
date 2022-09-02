package me.ipsum_amet.bikeplace.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import me.ipsum_amet.bikeplace.RegisterScreen
import me.ipsum_amet.bikeplace.Util.REGISTER_SCREEN
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

fun NavGraphBuilder.registerComposable(
    navigateToSignInScreen: () -> Unit,
    bikePlaceViewModel: BikePlaceViewModel,
) {
    composable(
        route = REGISTER_SCREEN,
    ) {



        RegisterScreen(
            bikePlaceViewModel = bikePlaceViewModel,
            navigateToSignInScreen = navigateToSignInScreen
        )
    }
}