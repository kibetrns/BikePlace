package me.ipsum_amet.bikeplace.data.model

import kotlinx.datetime.LocalDateTime
import me.ipsum_amet.bikeplace.data.dto.response.BikeDropOffAddress
import java.io.Serializable


data class BookingInfo(
    val bookingId: String,
    val dateBookingMade: LocalDateTime,
    var bikeDropOffAddress: BikeDropOffAddress?,
    val bikeLeaseActivation: LocalDateTime,
    val bikeLeaseExpiry: LocalDateTime,
    val userName: String,
    val bikeName:String,
    val amount: Double,
    val bikeReturnStatus: ReturnStatus,
)
