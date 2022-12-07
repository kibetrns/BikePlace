package me.ipsum_amet.bikeplace.data.dto.request

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.ipsum_amet.bikeplace.data.model.BikeDropOffAddress
import me.ipsum_amet.bikeplace.data.model.ReturnStatus
import me.ipsum_amet.bikeplace.data.model.TYPE

@Serializable
data class BookingsReq(
    @SerialName("Amount")
    val amount: Int,
    @SerialName("BikeId")
    val bikeId: String,
    @SerialName("BikeLeaseActivation")
    val bikeLeaseActivation: LocalDateTime,
    @SerialName("BikeLeaseExpiry")
    val bikeLeaseExpiry: LocalDateTime,
    @SerialName("BikeName")
    val bikeName: String,
    @SerialName("BikeType")
    val bikeType: TYPE,
    @SerialName("BikeDropOffLocation")
    val bikeDropOffLocation: BikeDropOffAddress?,
    @SerialName("UserName")
    val userName: String,
    @SerialName("UserId")
    val userId: String,
    @SerialName("UserPhoneNumber")
    val userPhoneNumber: Long,
    @SerialName("BikeReturnStatus")
    val bikeReturnStatus: ReturnStatus
)
// The date times are supposed to be String NOT LocalDateTime:::NB Remember That :)