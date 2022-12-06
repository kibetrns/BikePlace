package ipsum_amet.me.data.remote.dtos.requests.mpesa

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.ipsum_amet.bikeplace.data.model.ReturnStatus

@Serializable
data class EditReturnStatusBookingInfo(
    @SerialName("BikeReturnStatus")
    val bikeReturnStatus: ReturnStatus,
    @SerialName("BookingId")
    val bookingId: String
)
