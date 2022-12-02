package me.ipsum_amet.bikeplace.data.dto.response


import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.ipsum_amet.bikeplace.data.model.BookingInfo
import me.ipsum_amet.bikeplace.data.model.ReturnStatus

@Serializable
data class BookingsInfoRes(
    @SerialName("amount")
    val amount: Double,
    @SerialName("bikeDropOffAddress")
    val bikeDropOffAddress: BikeDropOffAddress?,
    @SerialName("bikeId")
    val bikeId: String,
    @SerialName("bikeLeaseActivation")
    val bikeLeaseActivation: LocalDateTime,
    @SerialName("bikeLeaseExpiry")
    val bikeLeaseExpiry: LocalDateTime,
    @SerialName("bikeName")
    val bikeName: String,
    @SerialName("bikeReturnStatus")
    val bikeReturnStatus: ReturnStatus,
    @SerialName("bookingId")
    val bookingId: String,
    @SerialName("dateBookingMade")
    val dateBookingMade: LocalDateTime,
    @SerialName("userId")
    val userId: String,
    @SerialName("userName")
    val userName: String,
    @SerialName("userPhoneNumber")
    val userPhoneNumber: Long
)
fun BookingsInfoRes.toBookingInfo(): BookingInfo {
    return BookingInfo(
        bookingId = bookingId,
        dateBookingMade = dateBookingMade,
        bikeDropOffAddress = bikeDropOffAddress,
        bikeLeaseActivation = bikeLeaseActivation,
        bikeLeaseExpiry = bikeLeaseExpiry,
        userName = userName,
        bikeName = bikeName,
        amount = amount,
        bikeReturnStatus = bikeReturnStatus
    )
}