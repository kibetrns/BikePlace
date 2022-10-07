package me.ipsum_amet.bikeplace.data.model

import com.google.firebase.Timestamp
import com.google.type.Date
import java.io.Serializable

data class Bike(
    var bikeId: String? = null,
    var userId: String? = null,
    var name: String? = null,
    var price: Double? = null,
    var imageUrl: String? = null,
    var description: String? = null,
    var isBooked: Boolean? = null,
    @Transient var postedAt: Timestamp? = null,
    @Transient var modifiedAt: Timestamp? = null,
    var condition: CONDITION? = null,
    var type: TYPE? = null
) : Serializable {
    fun toMap() = mapOf(
        "bikeId" to bikeId,
        "userId" to userId,
        "name" to name,
        "price" to price,
        "imageUrl" to imageUrl,
        "description" to description,
        "isBooked" to isBooked,
        "condition" to condition,
        "type" to type
    )
}

