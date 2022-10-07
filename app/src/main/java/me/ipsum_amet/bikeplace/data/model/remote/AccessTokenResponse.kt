package me.ipsum_amet.bikeplace.data.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AccessTokenResponse(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("expires_in")
    val expiresIn: String
) {

    fun toMap() = mapOf(
        "accessToken" to accessToken,
        "expiresIn" to expiresIn
    )
}
