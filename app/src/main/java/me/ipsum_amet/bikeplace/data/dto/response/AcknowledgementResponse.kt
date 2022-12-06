package ipsum_amet.me.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AcknowledgementResponse(
    @SerialName("status")
    val status: Status,
    @SerialName("message")
    val message: String
)

enum class Status() {
    SUCCESS, FAILED
}