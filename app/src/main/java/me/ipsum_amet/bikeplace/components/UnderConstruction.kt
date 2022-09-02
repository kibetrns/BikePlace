package me.ipsum_amet.bikeplace.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import me.ipsum_amet.bikeplace.R
import me.ipsum_amet.bikeplace.ui.theme.BikePlaceTheme

@Composable
fun UnderConstruction(navigateToRegisterScreen: () -> Unit) {

   Box(modifier = Modifier.fillMaxSize() ) {
       Image(
           painter = painterResource(id = R.drawable.c_undcons),
           contentDescription = stringResource(
               id = R.string.MAW
           ),
           modifier = Modifier.align(Alignment.Center)
       )
       TextButton(
           onClick = { navigateToRegisterScreen() },
           modifier = Modifier.align(Alignment.BottomCenter)
       ) {
           Text(
               text = stringResource(id = R.string.sign_up),
               letterSpacing = 1.sp,
               style = MaterialTheme.typography.subtitle1
           )
       }
   }
}



@Preview(name = "UnderConstruction", showBackground = true, showSystemUi = true)
@Composable
fun PUnderConstruction() {
    BikePlaceTheme {
        UnderConstruction(navigateToRegisterScreen = {})
    }
}