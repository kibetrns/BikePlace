package me.ipsum_amet.bikeplace.view.bikeDetails

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import kotlinx.datetime.LocalDateTime
import me.ipsum_amet.bikeplace.util.Action
import me.ipsum_amet.bikeplace.data.model.Bike
import me.ipsum_amet.bikeplace.viewmodel.BikePlaceViewModel
import java.util.*
import kotlin.math.min

@Composable
fun BikeDetailsScreen(
    bikePlaceViewModel: BikePlaceViewModel,
    selectedBike: Bike?,
    navigateToPreviousScreen: (Action) -> Unit,
    navigateToSummaryScreen: () -> Unit
) {
    val hoursToLease by remember { bikePlaceViewModel.hoursToLease }
    val leaseActivationTitle by remember { bikePlaceViewModel.leaseActivationTitle}
    var leaseActivationDateInput by remember { bikePlaceViewModel.leaseActivationDateInput }
    var leaseActivationTimeInput by remember { bikePlaceViewModel.leaseActivationTimeInput }
    val leaseExpiryTitle by remember { bikePlaceViewModel.leaseExpiryTitle }
    var leaseExpiryDateInput by remember { bikePlaceViewModel.leaseExpiryDateInput }
    var leaseExpiryTimeInput by remember { bikePlaceViewModel.leaseExpiryTimeInput }

    val totalPrice = bikePlaceViewModel.calculateTotalCheckoutPrice()






    val context = LocalContext.current


    val year: Int
    val month: Int
    val day: Int
    val hour: Int
    val minute: Int



    //val kotlinDateTime = LocalDateTime(leaseActivationDateInput, leaseActivationTimeInput)





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
            leaseActivationDateInput = if( month in 0..9 && dayOfMonth in 0..9 ) {
                "0$dayOfMonth-0$month-$year"
            } else if (month in 0..9 && dayOfMonth > 9) {
                "$dayOfMonth-0$month-$year"
            } else if (month > 9 && dayOfMonth in 0..9) {
                "0$dayOfMonth-$month-$year"
            }
            else {
                "$dayOfMonth-$month-$year"
            }
        }, year, month, day
    )

    val datePickerDialog2 = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            leaseExpiryDateInput = if( month in 0..9 && dayOfMonth in 0..9 ) {
                "0$dayOfMonth-0$month-$year"
            } else if (month in 0..9 && dayOfMonth > 9) {
                "$dayOfMonth-0$month-$year"
            } else if (month > 9 && dayOfMonth in 0..9) {
                "0$dayOfMonth-$month-$year"
            }
            else {
                "$dayOfMonth-$month-$year"
            }
        }, year, month, day
    )


    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            leaseActivationTimeInput = if( hour in 0..9 && minute in 0..9 ) {
                "0$hour:0$minute"
            } else if( minute in 0..9 && hour > 9 ) {
                "$hour:0$minute"
            } else if (hour in 0..9 && minute > 9 )
                "0$hour:$minute"
            else {
                "$hour:$minute"
            }
        }, hour, minute , true
    )
    val timePickerDialog2 = TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
           leaseExpiryTimeInput = if( hour in 0..9 && minute in 0..9 ) {
               "0$hour:0$minute"
           } else if( minute in 0..9 && hour > 9 ) {
               "$hour:0$minute"
           } else if (hour in 0..9 && minute > 9 )
               "0$hour:$minute"
           else {
               "$hour:$minute"
           }

        }, hour, minute , true
    )
    /*
     else if ( hour in 0..9 && minute in 0..9)
            "0$hour:0$minute"
     */


    Scaffold(
        topBar = {
            PlainAppBar(title = "") { action: Action ->
                if (action == Action.NO_ACTION) {
                    navigateToPreviousScreen(action)
                }
            }
        },
        content = {
            if (selectedBike != null) {
                if (totalPrice != null) {
                    BikeDetailsContent(
                        bike = selectedBike,
                        hoursToLease = hoursToLease,
                        onPayButtonClicked = {
                            //bikePlaceViewModel.makeMpesaPayment()
                            navigateToSummaryScreen()
                        },
                        onHoursToLeaseClicked = { bikePlaceViewModel.hoursToLease.value = it },
                        totalPrice = totalPrice,
                        leaseActivationTitle = leaseActivationTitle,
                        leaseActivationDateInput = leaseActivationDateInput,
                        leaseActivationTimeInput = leaseActivationTimeInput,
                        leaseExpiryTitle = leaseExpiryTitle,
                        leaseExpiryDateInput = leaseExpiryDateInput,
                        leaseExpiryTimeInput = leaseExpiryTimeInput,
                        onLeaseActivationDateClicked = {
                            datePickerDialog.show()
                            bikePlaceViewModel.leaseActivationDateInput.value = it

                        },
                        onLeaseActivationTimeClicked = {
                            timePickerDialog.show()
                            bikePlaceViewModel.leaseActivationTimeInput.value = it
                        },
                        onLeaseExpiryDateClicked = {
                            datePickerDialog2.show()
                            bikePlaceViewModel.leaseExpiryDateInput.value = it
                        },
                        onLeaseExpiryTimeClicked = {
                            timePickerDialog2.show()
                            bikePlaceViewModel.leaseExpiryTimeInput.value = it
                        },
                    )
                }

            }
        }
    )
}

