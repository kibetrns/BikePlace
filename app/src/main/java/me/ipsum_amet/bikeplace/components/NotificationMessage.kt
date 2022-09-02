package me.ipsum_amet.bikeplace.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel

@Composable
fun NotificationMessage(bikePlaceViewModel: BikePlaceViewModel) {
    val notificationState =  bikePlaceViewModel.popUpNotification.value
    val notificationMessage = notificationState?.GetContentOrNull()

    if(notificationMessage != null) {
        Toast.makeText(LocalContext.current, notificationMessage, Toast.LENGTH_LONG).show()
    }
}