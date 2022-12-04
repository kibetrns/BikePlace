package me.ipsum_amet.bikeplace.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BikeDropOffAddress(
    @SerialName("area")
    val area: String,
    @SerialName("moreInfo")
    val moreInfo: String,
    @SerialName("streetName")
    val streetName: String,
    @SerialName("title")
    val title: String
)
