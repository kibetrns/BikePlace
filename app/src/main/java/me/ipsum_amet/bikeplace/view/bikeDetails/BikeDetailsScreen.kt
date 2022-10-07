package me.ipsum_amet.bikeplace.view.bikeDetails

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import me.ipsum_amet.bikeplace.Util.Action
import me.ipsum_amet.bikeplace.data.model.Bike
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun BikeDetailsScreen(
    bikePlaceViewModel: BikePlaceViewModel,
    selectedBike: Bike?,
    navigateToPreviousScreen: (Action) -> Unit
) {
    val hoursToLease by remember { bikePlaceViewModel.hoursToLease }
    val leaseActivationTitle by remember { bikePlaceViewModel.leaseActivationTitle}
    var leaseActivationDateInput by remember { bikePlaceViewModel.leaseActivationDateInput }
    var leaseActivationTimeInput by remember { bikePlaceViewModel.leaseActivationTimeInput }
    val leaseExpiryTitle by remember { bikePlaceViewModel.leaseExpiryTitle }
    var leaseExpiryDateInput by remember { bikePlaceViewModel.leaseExpiryDateInput }
    var leaseExpiryTimeInput by remember { bikePlaceViewModel.leaseExpiryTimeInput }

    val context = LocalContext.current

    val year: Int
    val month: Int
    val day: Int
    val hour: Int
    val minute: Int


    val calendar = Calendar.getInstance()

    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)

    hour = calendar[Calendar.HOUR]
    minute = calendar[Calendar.MINUTE]

    calendar.time = Date()


    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            leaseActivationDateInput = "$dayOfMonth-$month-$year"
        }, year, month, day
    )

    val datePickerDialog2 = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            leaseExpiryDateInput = "$dayOfMonth-$month-$year"
        }, year, month, day
    )


    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            leaseActivationTimeInput = "$hour:$minute"
        }, hour, minute , true
    )
    val timePickerDialog2 = TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            leaseExpiryTimeInput = "$hour:$minute"
        }, hour, minute , true
    )

    Scaffold(
        topBar = {
            BikeDetailsAppBar(
                navigateToPreviousScreen = { action: Action ->
                    if ( action == Action.NO_ACTION ) {
                        navigateToPreviousScreen(action)
                    }
                }
            )
        },
        content = {
            if (selectedBike != null) {
                BikeDetailsContent(
                    bike = selectedBike,
                    hoursToLease = hoursToLease,
                    onPayButtonClicked = {
                        bikePlaceViewModel.makeMpesaPayment()
                    },
                    onHoursToLeaseClicked = { bikePlaceViewModel.hoursToLease.value = it },
                    totalPrice = bikePlaceViewModel.calculateTotalCheckoutPrice(),
                    leaseActivationTitle = leaseActivationTitle,
                    leaseActivationDateInput = leaseActivationDateInput,
                    leaseActivationTimeInput = leaseActivationTimeInput,
                    leaseExpiryTitle = leaseExpiryTitle,
                    leaseExpiryDateInput = leaseExpiryDateInput,
                    leaseExpiryTimeInput = leaseExpiryTimeInput,
                    onLeaseActivationDateClicked = {
                        datePickerDialog.show()
                    },
                    onLeaseActivationTimeClicked = {
                        timePickerDialog.show()
                    },
                    onLeaseExpiryDateClicked = {
                        datePickerDialog2.show()
                    },
                    onLeaseExpiryTimeClicked = {
                        timePickerDialog2.show()
                    },
                )
            }
        }
    )
}

