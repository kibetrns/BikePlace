package me.ipsum_amet.bikeplace.navigation.graphs

import SignInScreen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import me.ipsum_amet.bikeplace.RegisterScreen
import me.ipsum_amet.bikeplace.ResetPasswordScreen
import me.ipsum_amet.bikeplace.navigation.destinations.bikeComposable
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

fun NavGraphBuilder.authNavGraph(navController: NavHostController, bikePlaceViewModel: BikePlaceViewModel) {

    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Register.route
    ) {
        composable(route = AuthScreen.Register.route) {
            RegisterScreen(
                bikePlaceViewModel = bikePlaceViewModel,
                navigateToSignInScreen = {
                    navController.navigate(AuthScreen.SignIn.route) {
                        launchSingleTop
                    }
                }
            )
        }

        composable(route = AuthScreen.SignIn.route) {
            SignInScreen(
                navigateToRegisterScreen = {
                    navController.navigate(AuthScreen.SignIn.route) {
                        launchSingleTop = true
                        popUpTo(AuthScreen.Register.route)
                    }
                },
                navigateToListScreen = {
                    navController.popBackStack()
                    navController.navigate(Graph.SEARCH) {
                        launchSingleTop = true
                    }
                },
                navigateToResetPasswordScreen = {
                    navController.navigate(AuthScreen.Reset.route) {
                        launchSingleTop = true
                    }
                },
                bikePlaceViewModel = bikePlaceViewModel
            )
        }

        composable(route = AuthScreen.Reset.route) {
            ResetPasswordScreen(
                navigateToRegisterScreen = {
                    navController.navigate(AuthScreen.Register.route) {
                        launchSingleTop = true
                        popUpTo(AuthScreen.Register.route)
                    }
                }
            )
        }

    }
}
sealed class AuthScreen(val route: String) {
    object SignIn: AuthScreen(route = "signIn")
    object Register: AuthScreen(route = "register")
    object Reset: AuthScreen(route = "resetPassword")
}