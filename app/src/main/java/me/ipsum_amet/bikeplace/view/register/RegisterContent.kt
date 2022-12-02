package me.ipsum_amet.bikeplace.view.register

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import me.ipsum_amet.bikeplace.R
import me.ipsum_amet.bikeplace.util.L_PADDING
import me.ipsum_amet.bikeplace.util.SIGN_IN_IMAGE_HEIGHT
import me.ipsum_amet.bikeplace.util.XL_PADDING
import me.ipsum_amet.bikeplace.ui.theme.BikePlaceTheme
import me.ipsum_amet.bikeplace.view.bikeEntry.displayToast
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

@Composable
fun RegisterContent(
    fullName: String,
    onFullNameChange: (String) -> Unit,
    emailAddress: String,
    onEmailAddressChange: (String) -> Unit,
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmedPassword: String,
    bikePlaceViewModel: BikePlaceViewModel,
    onConfirmedPasswordChange: (String) -> Unit,
    navigateToSignInScreen: () -> Unit,
    isLoading: Boolean,
    context: Context
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        if(isLoading) {
            CircularProgressIndicator()
        }
        Image(
            painter = painterResource(id = R.drawable.user_reg),
            contentDescription = stringResource(
                id = R.string.sign_in_image
            ),
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
            var passwordVisible by rememberSaveable { mutableStateOf(false) }
            val focus = LocalFocusManager.current
            Text(
                text = stringResource(id = R.string.register),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(bottom = XL_PADDING)
                    .fillMaxWidth(),
            )
            OutlinedTextField(
                value = fullName,
                onValueChange = { onFullNameChange(it) },
                label = { Text(stringResource(id = R.string.enter_full_name)) },
                singleLine = true,
                placeholder = { Text(stringResource(id = R.string.full_name)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            )
            Spacer(modifier = Modifier.height(L_PADDING))
            OutlinedTextField(
                value = emailAddress,
                onValueChange = { onEmailAddressChange(it) },
                label = { Text(stringResource(id = R.string.enter_email_address)) },
                singleLine = true,
                placeholder = { Text(stringResource(id = R.string.email_address)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )
            Spacer(modifier = Modifier.height(L_PADDING))
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { onPhoneNumberChange(it) },
                label = { Text(stringResource(id = R.string.enter_phone_number)) },
                singleLine = true,
                placeholder = { Text(stringResource(id = R.string.phone_number)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            Spacer(modifier = Modifier.height(L_PADDING))
            OutlinedTextField(
                value = password,
                onValueChange = { onPasswordChange(it) },
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
            OutlinedTextField(
                value = confirmedPassword,
                onValueChange = { onConfirmedPasswordChange(it) },
                label = { Text(stringResource(id = R.string.confirm_password)) },
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
            Button(
                onClick = {
                    focus.clearFocus(force = true)
                    if(bikePlaceViewModel.validateRegistrationFields()) {
                        if(bikePlaceViewModel.validateSamePassword()) {
                            if( bikePlaceViewModel.validatePhoneNumber() ) {
                                bikePlaceViewModel.registerUser()
                                navigateToSignInScreen()
                            } else {
                                displayToast(
                                    context = context,
                                    "Phone number should be 10 digits long with no spaces and in the format 0712345678"
                                )
                            }
                        } else {
                            displayToast(context = context, "Passwords DOESN'T Match")
                        }
                    } else {
                        displayToast(context = context, "Fill in All Field(s) to Proceed")
                    }
                }
            ) {
                Text(text = stringResource(id = R.string.sign_up))
            }
            Spacer(modifier = Modifier.height(XL_PADDING))
            TextButton(
                onClick = {
                    navigateToSignInScreen()
                }) {
                Text(
                    text = stringResource(id = R.string.sign_in_instead),
                    letterSpacing = 1.sp,
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }

    }




}

@Preview(name = "RegisterContent", showBackground = true, showSystemUi = true)
@Composable
fun PRegisterContent() {
    val vm = hiltViewModel<BikePlaceViewModel>()

    BikePlaceTheme() {
        RegisterContent(
            fullName = "",
            onFullNameChange = {},
            emailAddress = "",
            onEmailAddressChange = {},
            phoneNumber = "",
            onPhoneNumberChange = {},
            password = "",
            onPasswordChange = {},
            confirmedPassword = "",
            bikePlaceViewModel = vm,
            onConfirmedPasswordChange = {},
            navigateToSignInScreen = {},
            isLoading = false,
            context = LocalContext.current
        )
    }
}