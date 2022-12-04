package me.ipsum_amet.bikeplace.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookingInfoReq(
    @SerialName("UserId")
    var userId: String,
    @SerialName("MpesaReceiptNumber")
    var mpesaReceiptNumber: String
)
