package me.ipsum_amet.bikeplace.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import me.ipsum_amet.bikeplace.ResetPasswordScreen
import me.ipsum_amet.bikeplace.Util.RESET_PASSWORD_SCREEN

fun NavGraphBuilder.resetPasswordComposable(
    navigateToRegisterScreen: () -> Unit
) {
    composable(
        route = RESET_PASSWORD_SCREEN
    ) {
        ResetPasswordScreen(navigateToRegisterScreen = navigateToRegisterScreen)
    }
}
