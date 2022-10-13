package me.ipsum_amet.bikeplace.data.model.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Testing(
    @SerialName("AccountReference")
    val accountReference: String,
    @SerialName("Amount")
    val amount: Int,
    @SerialName("BusinessShortCode")
    val businessShortCode: Int,
    @SerialName("CallBackURL")
    val callBackURL: String,
    @SerialName("PartyA")
    val partyA: Long,
    @SerialName("PartyB")
    val partyB: Int,
    @SerialName("Password")
    val password: String,
    @SerialName("PhoneNumber")
    val phoneNumber: Long,
    @SerialName("Timestamp")
    val timestamp: String,
    @SerialName("TransactionDesc")
    val transactionDesc: String,
    @SerialName("TransactionType")
    val transactionType: String
)