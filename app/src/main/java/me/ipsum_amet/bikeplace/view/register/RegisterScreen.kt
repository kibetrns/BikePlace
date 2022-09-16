package me.ipsum_amet.bikeplace


import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import me.ipsum_amet.bikeplace.ui.theme.BikePlaceTheme
import me.ipsum_amet.bikeplace.view.register.RegisterContent
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

@Composable
fun RegisterScreen(
    bikePlaceViewModel: BikePlaceViewModel,
    navigateToSignInScreen: () -> Unit,
) {
    var password by remember { bikePlaceViewModel.password }
    var confirmedPassword by remember { bikePlaceViewModel.confirmedPassword }

    var emailAddress by remember { bikePlaceViewModel.emailAddress }
    var fullName by remember { bikePlaceViewModel.fullName }

    var phoneNumber by remember { bikePlaceViewModel.phoneNumber }

    var isLoading by remember { bikePlaceViewModel.inProgress }

    val context = LocalContext.current

    RegisterContent(
        fullName = fullName,
        onFullNameChange = { bikePlaceViewModel.fullName.value = it },
        emailAddress = emailAddress,
        onEmailAddressChange = { bikePlaceViewModel.emailAddress.value = it },
        phoneNumber = phoneNumber,
        onPhoneNumberChange = { bikePlaceViewModel.phoneNumber.value = it },
        password = password,
        onPasswordChange = { bikePlaceViewModel.password.value = it },
        confirmedPassword = confirmedPassword,
        bikePlaceViewModel = bikePlaceViewModel,
        onConfirmedPasswordChange = { bikePlaceViewModel.confirmedPassword.value = it },
        navigateToSignInScreen = navigateToSignInScreen,
        isLoading = isLoading,
        context = context
    )

}

@Preview(name = "Register Screen", showBackground = true)
@Composable
fun PRegisterScreen() {
    val nc = rememberNavController()

    val vm = hiltViewModel<BikePlaceViewModel>()
    BikePlaceTheme {
        RegisterScreen(vm) {}
    }
}