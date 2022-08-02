package me.ipsum_amet.bikeplace

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import me.ipsum_amet.bikeplace.Util.*
import me.ipsum_amet.bikeplace.ui.theme.BikePlaceTheme

@Composable
fun RegisterScreen(navController: NavController) {
    var password by rememberSaveable { mutableStateOf("") }
    var confirmedPassword by rememberSaveable { mutableStateOf("")}
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    var emailAddress by rememberSaveable { mutableStateOf("") }
    var fullName by rememberSaveable { mutableStateOf("") }

    var phoneNumber by rememberSaveable { mutableStateOf("")}

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = R.drawable.user_reg),
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
                onValueChange = { fullName = it },
                label = { Text(stringResource(id = R.string.enter_full_name)) },
                singleLine = true,
                placeholder = { Text(stringResource(id = R.string.full_name)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            )
            Spacer(modifier = Modifier.height(L_PADDING))
            OutlinedTextField(
                value = emailAddress,
                onValueChange = { emailAddress = it },
                label = { Text(stringResource(id = R.string.enter_email_address)) },
                singleLine = true,
                placeholder = { Text(stringResource(id = R.string.email_address)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )
            Spacer(modifier = Modifier.height(L_PADDING))
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text(stringResource(id = R.string.enter_phone_number)) },
                singleLine = true,
                placeholder = { Text(stringResource(id = R.string.phone_number)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            Spacer(modifier = Modifier.height(L_PADDING))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
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
                onValueChange = { confirmedPassword = it },
                label = { Text(stringResource(id = R.string.confirm_password)) },
                singleLine = true,
                placeholder = { Text(stringResource(id = R.string.confirm_password)) },
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

                }
            ) {
                Text(text = stringResource(id = R.string.sign_up))
            }
            Spacer(modifier = Modifier.height(XL_PADDING))
            TextButton(
                onClick = {
                    navController.navigate("register_page") {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }) {
                Text(
                    text = stringResource(id = R.string.sign_in),
                    letterSpacing = 1.sp,
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }
    }
}

@Preview(name = "Register Screen", showBackground = true)
@Composable
fun PRegisterScreen() {
    val navController = rememberNavController()
    BikePlaceTheme {
        RegisterScreen(navController)
    }
}