import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import me.ipsum_amet.bikeplace.R
import me.ipsum_amet.bikeplace.Util.*
import me.ipsum_amet.bikeplace.ui.theme.BikePlaceTheme
import me.ipsum_amet.bikeplace.view.bikeEntry.displayToast
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

@Composable
fun SignInScreen(
    navigateToRegisterScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    navigateToResetPasswordScreen: () -> Unit,
    bikePlaceViewModel: BikePlaceViewModel
) {
    var password by remember { bikePlaceViewModel.password }
    var passwordVisible by remember { mutableStateOf(false) }

    var emailAddress by remember { bikePlaceViewModel.emailAddress}

    val alreadyLoggedIn = remember { bikePlaceViewModel.alreadyLoggedIn }
    val signedIn = bikePlaceViewModel.signedIn.value

    val context = LocalContext.current


    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = R.drawable.user_sign_in),
            contentDescription = stringResource(id = R.string.sign_in_image),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(SIGN_IN_IMAGE_HEIGHT)
                .fillMaxWidth()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.sign_in),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(bottom = XL_PADDING)
                    .fillMaxWidth(),
            )
            OutlinedTextField(
                value = emailAddress,
                onValueChange = { bikePlaceViewModel.emailAddress.value = it },
                label = { Text(stringResource(id = R.string.enter_email_address)) },
                singleLine = true,
                placeholder = { Text(stringResource(id = R.string.email_address)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )
            Spacer(modifier = Modifier.height(L_PADDING))
            OutlinedTextField(
                value = password,
                onValueChange = { bikePlaceViewModel.password.value = it },
                label = { Text(stringResource(id = R.string.enter_password)) },
                singleLine = true,
                placeholder = { Text(stringResource(id = R.string.password)) },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    // Localized description for accessibility services
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    // Toggle button to hide or display password
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
            )
            Spacer(modifier = Modifier.height(L_PADDING))
            Button(
                onClick = {
                    if (bikePlaceViewModel.validateSignInFields()) {
                        bikePlaceViewModel.loginUser(
                            emailAddress = bikePlaceViewModel.emailAddress.value,
                            password = bikePlaceViewModel.password.value
                        )
                        if (signedIn && !alreadyLoggedIn.value) {
                            alreadyLoggedIn.value = true
                           //navigateToHomeScreen(Action.NO_ACTION)
                            //navigateToHomeScreen(Action.GET_ALL_BIKES)
                            navigateToHomeScreen()
                        }
                    } else {
                        displayToast(context = context, "Fill in all Field(s) to Proceed")
                    }
                }
            ) {
                Text(text = stringResource(id = R.string.sign_in))
            }
            Spacer(modifier = Modifier.height(XL_PADDING))
            TextButton(
                onClick = {
                    navigateToRegisterScreen()
            }) {
               Text(
                    text = stringResource(id = R.string.create_account),
                    letterSpacing = 1.sp,
                    style = MaterialTheme.typography.subtitle1
                )
            }
            Spacer(modifier = Modifier.height(L_PADDING))
            TextButton(
                onClick = {
                    navigateToResetPasswordScreen()
                }) {
                Text(
                    text = stringResource(id = R.string.reset_password),
                    letterSpacing = 1.sp,
                    style = MaterialTheme.typography.subtitle1
                )
            }

        }
    }
}

@Preview(name = "Login Screen", showBackground = true)
@Composable
fun PLoginScreen() {
    val navController = rememberNavController()
    val vm = hiltViewModel<BikePlaceViewModel>()
    BikePlaceTheme {
        SignInScreen(
            navigateToRegisterScreen = {},
            navigateToHomeScreen = {},
            navigateToResetPasswordScreen = {},
            bikePlaceViewModel = vm
        )
    }
}