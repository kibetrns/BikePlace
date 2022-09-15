package me.ipsum_amet.bikeplace.data.model

data class User(
    var userId: String? = null,
    var fullName: String? = null,
    var phoneNumber: String? = null,
    var imageUrl: String? = null,
    var location: String? =null,
    var orderedBikes: List<Bike>? = null
) {
    fun toMap() = mapOf(
        "userId" to userId,
        "fullName" to fullName,
        "phoneNumber" to phoneNumber,
        "imageUrl" to imageUrl,
        "location" to location,
        "orderBikes" to orderedBikes,
    )
}

